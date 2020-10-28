package com.engineblue.fuelprice

import androidx.multidex.MultiDexApplication
import com.engineblue.fuelprice.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FuelApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@FuelApplication)
            // modules
            modules(appModules)
        }
    }
}