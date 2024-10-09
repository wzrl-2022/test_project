package com.example.testproject.ui.common.phonenumbertextfield.data

import androidx.annotation.DrawableRes
import com.example.testproject.R
import java.util.*

data class CountryData(
    private val cCodes: String,
    val countryPhoneCode: String = "+90",
    val cNames:String = "tr",
    @DrawableRes val flagResID: Int = R.drawable.tr
) {
    val countryCode = cCodes.lowercase(Locale.getDefault())
}
