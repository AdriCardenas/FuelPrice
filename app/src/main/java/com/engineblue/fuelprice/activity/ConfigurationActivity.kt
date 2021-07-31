package com.engineblue.fuelprice.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.callback.SelectFuelProductListener
import com.engineblue.fuelprice.databinding.ConfigurationActivityBinding
import com.engineblue.fuelprice.fragment.ConfigureFuelProductOptionFragment
import com.engineblue.presentation.entity.FuelProductDisplayModel

class ConfigurationActivity : AppCompatActivity(), SelectFuelProductListener {

    private lateinit var binding: ConfigurationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ConfigurationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ConfigureFuelProductOptionFragment.newInstance())
            .commitNow()
    }

    override fun selectProduct(product: FuelProductDisplayModel) {
        startActivity(Intent(this, MainActivity::class.java))
    }

}
