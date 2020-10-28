package com.engineblue.presentation.viewmodel

import com.engineblue.domain.useCasesContract.preferences.SaveBoolean


class OnBoardingViewModel(
    private val saveBooleanPreference: SaveBoolean,
    scope: ViewModelScope = viewModelScope()
) {
    fun saveBooleanPreference(key: String, value: Boolean) {
        saveBooleanPreference.saveBoolean(key, value)
    }
}