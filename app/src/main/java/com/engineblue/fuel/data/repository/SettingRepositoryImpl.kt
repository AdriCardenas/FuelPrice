package com.engineblue.fuel.data.repository

import com.engineblue.fuel.data.datasource.PreferenceSettings
import com.engineblue.fuel.domain.repository.SettingRepository

class SettingRepositoryImpl(private val settingsDataSource: PreferenceSettings) :
    SettingRepository {
    override fun saveString(key: String, string: String) {
        settingsDataSource.saveString(key, string)
    }

    override fun loadString(key: String, defaultValue: String): String =
        settingsDataSource.loadString(key, defaultValue)

    override fun saveBoolean(key: String, boolean: Boolean) {
        settingsDataSource.saveBoolean(key, boolean)
    }

    override fun loadBoolean(key: String, defaultValue: Boolean): Boolean =
        settingsDataSource.loadBoolean(key, defaultValue)

    override fun saveInt(key: String, int: Int) {
        settingsDataSource.saveInt(key, int)
    }

    override fun loadInt(key: String, defaultValue: Int): Int =
        settingsDataSource.loadInt(key, defaultValue)
}