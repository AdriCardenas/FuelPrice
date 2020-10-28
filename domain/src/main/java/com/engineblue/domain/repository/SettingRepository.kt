package com.engineblue.domain.repository

interface SettingRepository {
    fun saveString(key: String, string: String)
    fun loadString(key: String, defaultValue: String): String

    fun saveBoolean(key: String, boolean: Boolean)
    fun loadBoolean(key: String, defaultValue: Boolean): Boolean

    fun saveInt(key: String, int: Int)
    fun loadInt(key: String, defaultValue: Int): Int
}