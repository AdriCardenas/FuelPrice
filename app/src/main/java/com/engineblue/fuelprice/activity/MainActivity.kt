package com.engineblue.fuelprice.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.fragment.StationListFragment

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, StationListFragment.newInstance())
            .commitNow()
    }
}
