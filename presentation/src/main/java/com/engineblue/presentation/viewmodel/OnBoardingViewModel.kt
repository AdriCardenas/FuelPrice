package com.engineblue.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.engineblue.domain.useCasesContract.preferences.SaveBoolean


class OnBoardingViewModel(
    private val saveBooleanPreference: SaveBoolean
) : ViewModel() {
    fun saveOnboardingChecked(key: String) {
        saveBooleanPreference.saveBoolean(key, false)
    }
}