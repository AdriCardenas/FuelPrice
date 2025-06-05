package com.engineblue.fuel.fuelprice

import androidx.multidex.MultiDexApplication
import com.engineblue.fuel.fuelprice.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FuelApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

//        RequestConfiguration.Builder().setTestDeviceIds(listOf("881B3CDEFCD2ACE568F2D55F9FDEB7E7"))

        startKoin {
            // Android context
            androidContext(this@FuelApplication)
            // modules
            modules(appModules)
        }
    }
}