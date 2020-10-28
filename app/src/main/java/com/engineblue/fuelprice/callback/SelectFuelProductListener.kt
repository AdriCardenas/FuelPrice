package com.engineblue.fuelprice.callback

import com.engineblue.presentation.entity.FuelProductDisplayModel

interface SelectFuelProductListener {
    fun selectProduct(product: FuelProductDisplayModel)
}