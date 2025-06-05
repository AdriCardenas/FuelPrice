package com.engineblue.fuel.presentation.useCases.preferences

import com.engineblue.fuel.domain.repository.SettingRepository
import com.engineblue.fuel.domain.useCasesContract.preferences.SaveBoolean

class SaveBooleanImpl(private val settingRepository: SettingRepository) :
    com.engineblue.fuel.domain.useCasesContract.preferences.SaveBoolean {
    override fun saveBoolean(key: String, value: Boolean) {
        settingRepository.saveBoolean(key, value)
    }
}