package com.engineblue.fuel.domain.useCasesContract.preferences

interface SaveBoolean {
    fun saveBoolean(key: String, value: Boolean = false)
}