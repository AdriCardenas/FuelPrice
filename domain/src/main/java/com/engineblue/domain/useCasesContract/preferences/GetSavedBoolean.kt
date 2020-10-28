package com.engineblue.domain.useCasesContract.preferences

interface GetSavedBoolean {
    fun getSavedBoolean(key: String, boolean: Boolean = false): Boolean
}