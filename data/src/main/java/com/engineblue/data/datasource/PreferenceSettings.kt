package com.engineblue.data.datasource

import com.engineblue.domain.entity.FuelEntity

interface PreferenceSettings {
    fun saveProduct(id: String, name: String)
    fun getSavedProduct(): FuelEntity

    fun saveString(key: String, string: String)
    fun loadString(key: String, defaultValue: String): String

    fun saveBoolean(key: String, boolean: Boolean)
    fun loadBoolean(key: String, defaultValue: Boolean): Boolean

    fun saveInt(key: String, int: Int)
    fun loadInt(key: String, defaultValue: Int): Int
}