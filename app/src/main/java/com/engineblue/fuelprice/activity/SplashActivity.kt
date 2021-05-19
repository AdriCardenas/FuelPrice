package com.engineblue.fuelprice.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.databinding.LauncherActivityBinding
import com.engineblue.fuelprice.databinding.OnboardingActivityBinding
import com.google.android.gms.ads.MobileAds

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: LauncherActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val darkMode = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(getString(R.string.pref_dark_mode), "1") ?: "1"

        val values =
            resources?.getStringArray(R.array.pref_entries_dark_mode_values) ?: arrayOf(
                "1",
                "2",
                "3"
            )

        when (darkMode) {
            values[1] -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            values[2] -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        super.onCreate(savedInstanceState)
        binding = LauncherActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)

        val firstStart = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(getString(R.string.pref_first_start), true)

        if(firstStart){
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }else{
            startActivity(Intent(this, ConfigurationActivity::class.java))
        }

        finish()
    }
}
