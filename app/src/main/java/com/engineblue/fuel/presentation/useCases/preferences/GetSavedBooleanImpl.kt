package com.engineblue.fuel.presentation.useCases.preferences

import com.engineblue.fuel.domain.repository.SettingRepository
import com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedBoolean

class GetSavedBooleanImpl(private val settingRepository: SettingRepository) :
    com.engineblue.fuel.domain.useCasesContract.preferences.GetSavedBoolean {
    override fun getSavedBoolean(key: String, boolean: Boolean): Boolean =
        settingRepository.loadBoolean(key, boolean)

}