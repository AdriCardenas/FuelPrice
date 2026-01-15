package com.engineblue.fuel.domain.useCases.preferences

import com.engineblue.fuel.domain.repository.SettingRepository
import com.engineblue.fuel.domain.useCasesContract.preferences.SaveBoolean

class SaveBooleanImpl(private val settingRepository: SettingRepository) :
    SaveBoolean {
    override fun saveBoolean(key: String, value: Boolean) {
        settingRepository.saveBoolean(key, value)
    }
}