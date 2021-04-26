package com.engineblue.fuelprice.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.engineblue.fuelprice.BuildConfig
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.callback.SelectFuelProductListener
import com.engineblue.fuelprice.callback.SelectLocationListener
import com.engineblue.fuelprice.fragment.ConfigurationLocationFragment
import com.engineblue.fuelprice.fragment.ConfigureFuelProductOptionFragment
import com.engineblue.fuelprice.fragment.StationListFragment
import com.engineblue.fuelprice.utils.toast
import com.engineblue.presentation.entity.FuelProductDisplayModel

class ConfigurationActivity : AppCompatActivity(), SelectFuelProductListener,
    SelectLocationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuration_activity)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ConfigureFuelProductOptionFragment.newInstance())
            .commitNow()
    }

    override fun selectProduct(product: FuelProductDisplayModel) {
        if (BuildConfig.DEBUG) {
            toast("${product.name} seleccionado")
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ConfigurationLocationFragment.newInstance())
            .commitNow()
    }

    override fun onLocationSelected() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}
