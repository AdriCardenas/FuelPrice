package com.engineblue.fuel.domain.useCasesContract.preferences

interface GetSavedBoolean {
    operator fun invoke(key: String, boolean: Boolean = false): Boolean
}