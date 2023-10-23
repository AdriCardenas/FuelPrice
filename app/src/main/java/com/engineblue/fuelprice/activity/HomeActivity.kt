package com.engineblue.fuelprice.activity

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.core.ui.AppTheme
import com.engineblue.fuelprice.screen.ConfigurationFuelScreen
import com.engineblue.fuelprice.screen.HomeScreen
import com.engineblue.fuelprice.screen.OnBoardingScreen
import com.engineblue.fuelprice.utils.toast
import com.engineblue.presentation.viewmodel.ListStationsViewModel
import com.engineblue.presentation.viewmodel.RoutingViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: RoutingViewModel by viewModel()
    private val stationViewModel: ListStationsViewModel by viewModel()

    private var location: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var firstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

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
                        if (firstLoad) {
                            checkLocationPermission()
                            firstLoad = false
                        }
                        HomeScreen(viewModel = stationViewModel) {
                            navController.navigate("configuration_fuel") {
                                popUpTo("home")
                            }
                        }
//                        { onBackPressedDispatcher.onBackPressed() }
                    }
                }
            }

        }
    }

    private fun checkLocationPermission() {
        Dexter.withContext(this).withPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    getCurrentLocation()
                } else {
                    // Permission request was denied.
                    toast(
                        getString(R.string.location_permission_denied),
                        Snackbar.LENGTH_SHORT
                    )
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: List<PermissionRequest?>?,
                token: PermissionToken?
            ) {
                //TODO MANUAL LOCATION
                toast(R.string.location_permission_denied)
            }
        })
            .withErrorListener {
                toast(it.name)
            }.check()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            this.location = location
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                this.location = it.result
                stationViewModel.setLocation(location?.latitude, location?.longitude)
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