package com.engineblue.fuel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.engineblue.fuel.domain.useCasesContract.preferences.SaveBoolean


class OnBoardingViewModel(
    private val saveBooleanPreference: com.engineblue.fuel.domain.useCasesContract.preferences.SaveBoolean
) : ViewModel() {
    fun saveOnboardingChecked(key: String) {
        saveBooleanPreference.saveBoolean(key, true)
    }
}