package com.engineblue.fuelprice.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.databinding.LauncherActivityBinding
import com.engineblue.presentation.viewmodel.SplashViewModel
import com.google.android.gms.ads.MobileAds
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: LauncherActivityBinding

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
//        val darkMode = PreferenceManager.getDefaultSharedPreferences(this)
//            .getString(getString(R.string.pref_dark_mode), "1") ?: "1"
//
//        val values =
//            resources?.getStringArray(R.array.pref_entries_dark_mode_values) ?: arrayOf(
//                "1",
//                "2",
//                "3"
//            )
//
//        when (darkMode) {
//            values[1] -> {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            }
//            values[2] -> {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//            }
//            else -> {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//        }

        super.onCreate(savedInstanceState)
        binding = LauncherActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)

        val firstStart = viewModel.getPreferencesFirstStart(getString(R.string.pref_first_start))

        if (firstStart) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        } else {
            val selectedFuel = viewModel.getSelectedFuel()
            if (selectedFuel.id != null && selectedFuel.name != null) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, ConfigurationActivity::class.java))
            }
        }

        finish()
    }
}
