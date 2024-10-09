package com.example.testproject.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.testproject.R
import com.example.testproject.helpers.RefreshTokenAction
import com.example.testproject.ui.chats.ChatsScreen
import com.example.testproject.ui.confirmphone.ConfirmPhoneScreen
import com.example.testproject.ui.editprofile.EditProfileScreen
import com.example.testproject.ui.profile.ProfileScreen
import com.example.testproject.ui.registration.RegistrationScreen
import com.example.testproject.ui.signin.SignInScreen
import com.example.testproject.ui.splash.SplashContent
import com.example.testproject.ui.theme.TestProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestProjectTheme {
                val navController = rememberNavController()

                val viewModel = hiltViewModel<MainViewModel>()

                RefreshTokenAction.create {
                    viewModel.logoutUser()
                }

                val startDestination by viewModel.isLoggedFlow.collectAsState()
                LaunchedEffect(Unit) {
                    viewModel.readCurrentUser()
                }

                val padding = dimensionResource(R.dimen.padding_medium)
                val destination: Any = when (startDestination) {
                    StartDestination.INITIAL -> Splash
                    StartDestination.NOT_LOGGED -> SignIn
                    StartDestination.LOGGED -> Chats
                }
                NavHost(
                    navController = navController,
                    startDestination = destination
                ) {
                    composable<Splash> {
                        SplashContent()
                    }

                    composable<SignIn> {
                        SignInScreen(
                            onOpenConfirmPhone = { phoneNumber ->
                                navController.navigate(ConfirmPhone(phoneNumber))
                            },
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize()
                        )
                    }

                    composable<ConfirmPhone> { backStackEntry ->
                        val confirmPhone = backStackEntry.toRoute<ConfirmPhone>()
                        ConfirmPhoneScreen(
                            phoneNumber = confirmPhone.phoneNumber,
                            onNavigateRegistration = { phoneNumber ->
                                viewModel.readCurrentUser()
                                navController.navigate(Registration(phoneNumber)) {
                                    popUpTo<SignIn> {
                                        inclusive = true
                                    }
                                }
                            },
                            onNavigateChats = {
                                navController.navigate(Chats) {
                                    viewModel.readCurrentUser()
                                    popUpTo<SignIn> {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize()
                        )
                    }

                    composable<Registration> { backStackEntry ->
                        val register = backStackEntry.toRoute<Registration>()
                        RegistrationScreen(
                            phoneNumber = register.phoneNumber,
                            onChats = {
                                navController.navigate(Chats) {
                                    popUpTo<Registration> {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize()
                        )
                    }

                    composable<Chats> {
                        ChatsScreen(
                            onOpenProfile = { navController.navigate(Profile) },
                            onLogout = {
                                viewModel.logoutUser()
                                navController.navigate(Splash)
                            },
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize()
                        )
                    }

                    composable<Profile> {
                        ProfileScreen(
                            onOpenEditProfile = { navController.navigate(EditProfile) },
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize()
                        )
                    }

                    composable<EditProfile> {
                        EditProfileScreen(
                            modifier = Modifier
                                .padding(padding)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}