package com.engineblue.fuel.domain.useCasesContract.preferences

interface GetSavedBoolean {
    fun getSavedBoolean(key: String, boolean: Boolean = false): Boolean
}