package com.engineblue.fuelprice.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.engineblue.fuelprice.core.ui.AppTheme
import com.engineblue.fuelprice.screen.ConfigurationFuelScreen
import com.engineblue.fuelprice.screen.HomeScreen
import com.engineblue.fuelprice.screen.OnboardingScreen
import com.engineblue.presentation.viewmodel.RoutingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: RoutingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            AppTheme {

                NavHost(navController = navController, startDestination = "onboarding") {
                    composable("onboarding") {
                        OnboardingScreen {
                            navController.navigate("configuration_fuel") {
                                popUpTo("configuration_fuel")
                            }
                        }
                    }
                    composable("configuration_fuel") {
                        ConfigurationFuelScreen {
                            navController.navigate("home") {
                                popUpTo("home")
                            }
                        }
                    }
                    composable("home") {
                        HomeScreen { onBackPressedDispatcher.onBackPressed() }
                    }
                }
            }

        }
    }
}