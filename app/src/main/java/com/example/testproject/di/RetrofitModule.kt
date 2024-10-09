package com.example.testproject.di

import com.example.testproject.authorization.ITokenManager
import com.example.testproject.helpers.RefreshTokenAction
import com.example.testproject.helpers.moshi.DateAdapter
import com.serjltt.moshi.adapters.FallbackOnNull
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Authorized

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Unauthorized

private const val API_BASE_URL = "https://plannerok.ru/api/v1/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val moshi = Moshi.Builder()
        .add(DateAdapter())
        .add(Wrapped.ADAPTER_FACTORY)
        .add(FallbackOnNull.ADAPTER_FACTORY)
        .build()

    private val baseOkHttpClientBuilder = OkHttpClient.Builder()
        .callTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .sslSocketFactory(
            SSLContext.getInstance("SSL").apply {
                init(null, trustAllCerts, SecureRandom())
            }.socketFactory,
            trustAllCerts[0] as (X509TrustManager)
        )
        .hostnameVerifier { _, _ ->
            true
        }

    private val baseRetrofitBuilder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))

    @Provides
    @Singleton
    @Authorized
    internal fun provideAuthorizedOkHttpClient(tokenManager: ITokenManager): OkHttpClient =
        baseOkHttpClientBuilder
            .addInterceptor(AuthorizedInterceptor(tokenManager))
            .build()

    @Provides
    @Singleton
    @Unauthorized
    internal fun provideUnauthorizedOkHttpClient(): OkHttpClient =
        baseOkHttpClientBuilder
            .build()

    @Provides
    @Singleton
    @Authorized
    fun provideAuthorizedRetrofit(@Authorized okHttpClient: OkHttpClient): Retrofit =
        baseRetrofitBuilder
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @Unauthorized
    fun provideUnauthorizedRetrofit(@Unauthorized okHttpClient: OkHttpClient): Retrofit =
        baseRetrofitBuilder
            .client(okHttpClient)
            .build()
}

class AuthorizedInterceptor(
    private val tokenManager: ITokenManager
) : Interceptor {
    private val sync = Any()
    private var isRefreshing = false

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(sync) {
            while (isRefreshing) {
                sync.wait()
            }
        }

        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer ${tokenManager.getTokens().accessToken}")
        val request = requestBuilder.build()
        var result = chain.proceed(request)
        if (result.code == 401) {
            synchronized(sync) {
                if (!isRefreshing) {
                    isRefreshing = true

                    result.close()
                    try {
                        refreshToken(chain)
                    } catch (e: HttpException) {
                        throw e
                    }

                    isRefreshing = false
                    sync.notifyAll()
                }
            }

            synchronized(sync) {
                while (isRefreshing) {
                    sync.wait()
                }
            }

            requestBuilder.addHeader("Authorization", "Bearer ${tokenManager.getTokens().accessToken}")
            result = chain.proceed(requestBuilder.build())
        }

        return result
    }

    private fun refreshToken(chain: Interceptor.Chain) {
        val refreshRequest = Request.Builder()
            .url("${API_BASE_URL}users/refresh-token/")
            .post("{\n\t\"refresh_token\": \"${tokenManager.getTokens().refreshToken}\"\n}".toRequestBody("application/json".toMediaType()))
        val refreshResult = chain.proceed(refreshRequest.build())

        if (refreshResult.code == 200) {
            val jsonReader = JsonReader.of(refreshResult.body?.source()!!)
            jsonReader.beginObject()
            var refreshToken = ""
            var accessToken = ""
            while (jsonReader.hasNext()) {
                val name = jsonReader.nextName()
                if ("refresh_token" == name) {
                    refreshToken = jsonReader.nextString()
                } else if ("access_token" == name) {
                    accessToken = jsonReader.nextString()
                } else {
                    jsonReader.skipValue()
                }
            }
            jsonReader.endObject()

            tokenManager.saveTokens(accessToken, refreshToken)
        } else if (refreshResult.code == 403) {
            RefreshTokenAction.getInstance().onFail()
        }

        refreshResult.close()
    }
}

val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

    }

    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return arrayOf()
    }
})