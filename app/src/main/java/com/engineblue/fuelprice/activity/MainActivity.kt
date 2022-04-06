package com.engineblue.fuelprice.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.databinding.MainActivityBinding
import com.engineblue.presentation.viewmodel.RoutingViewModel
import com.google.android.gms.ads.MobileAds
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private val viewModel: RoutingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_main)

        navGraph.setStartDestination(getInitialSection())
        navController.graph = navGraph
    }

    private fun getInitialSection(): Int {
        val firstStart = viewModel.getPreferencesFirstStart(getString(R.string.pref_first_start))

        return if (firstStart) {
            R.id.onboarding_fragment
        } else {
            val selectedFuel = viewModel.getSelectedFuel()
            if (selectedFuel.id != null && selectedFuel.name != null) {
                R.id.list_stations
            } else {
                R.id.configuration_fuel_product
            }
        }
    }
}
