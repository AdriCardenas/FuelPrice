package com.engineblue.fuelprice

import androidx.multidex.MultiDexApplication
import com.engineblue.fuelprice.di.appModules
import com.google.android.gms.ads.RequestConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FuelApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        RequestConfiguration.Builder().setTestDeviceIds(listOf("881B3CDEFCD2ACE568F2D55F9FDEB7E7"))

        startKoin {
            // Android context
            androidContext(this@FuelApplication)
            // modules
            modules(appModules)
        }
    }
}