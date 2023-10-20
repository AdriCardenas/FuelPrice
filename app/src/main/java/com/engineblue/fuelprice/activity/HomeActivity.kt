package com.engineblue.fuelprice.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.core.ui.AppTheme
import com.engineblue.fuelprice.screen.ConfigurationFuelScreen
import com.engineblue.fuelprice.screen.HomeScreen
import com.engineblue.fuelprice.screen.OnBoardingScreen
import com.engineblue.presentation.viewmodel.RoutingViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: RoutingViewModel by viewModel()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val startDestination =
                getStartDestination()

            AppTheme {

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable("onboarding") {
                        OnBoardingScreen {
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
                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@HomeActivity)
                        HomeScreen(fusedLocationClient = fusedLocationClient) { onBackPressedDispatcher.onBackPressed() }
                    }
                }
            }

        }
    }

    @Composable
    private fun getStartDestination(): String {
        val firstStart =
            viewModel.getPreferencesFirstStart(getString(R.string.pref_first_start))

        return if (firstStart) {
            "onboarding"
        } else {
            val selectedFuel = viewModel.getSelectedFuel()
            if (selectedFuel.id != null && selectedFuel.name != null) {
                "home"
            } else {
                "configuration_fuel"
            }
        }
    }
}