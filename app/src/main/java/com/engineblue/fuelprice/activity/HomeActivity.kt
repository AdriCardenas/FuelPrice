package com.engineblue.fuelprice.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.core.components.OnboardingItem
import com.engineblue.fuelprice.core.ui.AppTheme
import com.engineblue.fuelprice.screen.OnBoardingScreen
import com.engineblue.presentation.entity.OnBoardingItemDisplayModel
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
//                Scaffold(
//                    topBar = { FuelTopAppBar(stringResource(R.string.fuel_price_title)) { onBackPressedDispatcher.onBackPressed() }  },
//                    content = { padding ->
                NavHost(navController = navController, startDestination = "onboarding") {
                    composable("onboarding") {
                        OnBoardingScreen {
                            viewModel.finishOnBoarding(getString(R.string.pref_first_start))
//                            navController.navigate("vacations_management") {
//                                popUpTo("vacations_status")
//                            }
                        }
                    }
                }
            }
//                )

        }
    }
}