package com.engineblue.presentation.useCases.preferences

import com.engineblue.domain.repository.SettingRepository
import com.engineblue.domain.useCasesContract.preferences.SaveBoolean

class SaveBooleanImpl(private val settingRepository: SettingRepository) : SaveBoolean {
    override fun saveBoolean(key: String, value: Boolean) {
        settingRepository.saveBoolean(key, value)
    }
}