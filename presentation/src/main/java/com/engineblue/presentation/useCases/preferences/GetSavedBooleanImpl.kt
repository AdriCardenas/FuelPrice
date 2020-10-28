package com.engineblue.presentation.useCases.preferences

import com.engineblue.domain.repository.SettingRepository
import com.engineblue.domain.useCasesContract.preferences.GetSavedBoolean

class GetSavedBooleanImpl(private val settingRepository: SettingRepository) : GetSavedBoolean {
    override fun getSavedBoolean(key: String, boolean: Boolean): Boolean =
        settingRepository.loadBoolean(key, boolean)

}