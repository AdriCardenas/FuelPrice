package com.engineblue.fuelprice.preferences

import android.content.Context
import android.content.SharedPreferences
import com.engineblue.data.datasource.PreferenceSettings
import com.engineblue.domain.entity.FuelEntity
import com.engineblue.fuelprice.utils.APP_PREFERENCES
import com.engineblue.fuelprice.utils.PREFERENCES_ID_PRODUCT_SELECTED
import com.engineblue.fuelprice.utils.PREFERENCES_NAME_PRODUCT_SELECTED

class PreferenceManager(context: Context) : PreferenceSettings {

    private val sharedPreferences =
        context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    private fun getEditor() = sharedPreferences.edit()

    private fun saveEditor(editor: SharedPreferences.Editor) {
        editor.apply()
    }


    override fun saveProduct(id: String, name: String) {
        val editor = getEditor()

        editor.putString(PREFERENCES_ID_PRODUCT_SELECTED, id)
        editor.putString(PREFERENCES_NAME_PRODUCT_SELECTED, name)

        saveEditor(editor)
    }

    override fun getSavedProduct(): FuelEntity =
        FuelEntity(
            id = sharedPreferences.getString(PREFERENCES_ID_PRODUCT_SELECTED, null),
            name = sharedPreferences.getString(PREFERENCES_NAME_PRODUCT_SELECTED, null),
        )

    override fun saveString(key: String, string: String) {
        val editor = getEditor()

        editor.putString(key, string)

        saveEditor(editor)
    }

    override fun loadString(key: String, defaultValue: String) =
        sharedPreferences.getString(key, defaultValue) ?: defaultValue

    override fun saveBoolean(key: String, boolean: Boolean) {
        val editor = getEditor()

        editor.putBoolean(key, boolean)

        saveEditor(editor)
    }

    override fun loadBoolean(key: String, defaultValue: Boolean) =
        sharedPreferences.getBoolean(key, defaultValue)

    override fun saveInt(key: String, int: Int) {
        val editor = getEditor()

        editor.putInt(key, int)

        saveEditor(editor)
    }

    override fun loadInt(key: String, defaultValue: Int) =
        sharedPreferences.getInt(key, defaultValue)

}