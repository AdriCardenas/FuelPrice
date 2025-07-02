package com.engineblue.fuel.fuelprice.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.engineblue.fuel.fuelprice.core.ui.AppTheme
import com.engineblue.fuel.fuelprice.screen.ConfigurationFuelScreen
import com.engineblue.fuel.fuelprice.screen.HomeScreen
import com.engineblue.fuel.fuelprice.screen.OnBoardingScreen
import com.engineblue.fuel.presentation.viewmodel.ListStationsViewModel
import com.engineblue.fuel.presentation.viewmodel.RoutingViewModel
import com.fuel.engineblue.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: RoutingViewModel by viewModel()
    private val stationViewModel: ListStationsViewModel by viewModel()

    private var location: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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
                                popUpTo("onboarding") {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    composable("configuration_fuel") {
                        ConfigurationFuelScreen {
                            navController.navigate("home") {
                                popUpTo("configuration_fuel") {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    }
                    composable("home") {
                        HomeScreen(
                            viewModel = stationViewModel,
                            navigateToMaps = ::navigateToMaps,
                            getCurrentLocation = ::getCurrentLocation,
                            onSettingClicked = {
                                navController.navigate("configuration_fuel") {
                                    popUpTo("home")
                                }
                            })
                    }

                    composable("home") {
                        HomeScreen(
                            viewModel = stationViewModel,
                            navigateToMaps = ::navigateToMaps,
                            getCurrentLocation = ::getCurrentLocation,
                            onSettingClicked = {
                                navController.navigate("configuration_fuel") {
                                    popUpTo("home")
                                }
                            })
                    }
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()

        stationViewModel.checkLoadStations()
    }

    private fun navigateToMaps(location: Location, name:String) {
        val latitude = location.latitude
        val longitude = location.longitude

        val gmmIntentUri = "geo:$latitude,$longitude?q=$latitude,$longitude($name)"
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri.toUri())
        mapIntent.setPackage("com.google.android.apps.maps") // Specify Google Maps package

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        }else {
            // Google Maps app is not installed, handle this (e.g., show a Toast or open in browser)
            val webUri = "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude".toUri()
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            if (webIntent.resolveActivity(packageManager) != null) {
                startActivity(webIntent)
            } else {
                // No app can handle even the web URL
                // Show an error message
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            this.location = location
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                this.location = it.result
                stationViewModel.setLocation(location?.latitude?:0.0, location?.longitude?: 0.0)
                stationViewModel.loadStations()
            } else {
                stationViewModel.loadStations()
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