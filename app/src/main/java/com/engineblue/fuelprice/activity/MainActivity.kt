package com.engineblue.fuelprice.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.databinding.MainActivityBinding
import com.engineblue.fuelprice.fragment.StationListFragment

class MainActivity: AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, StationListFragment.newInstance())
            .commitNow()
    }
}
