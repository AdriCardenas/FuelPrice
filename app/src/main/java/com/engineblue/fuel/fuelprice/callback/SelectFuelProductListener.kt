package com.engineblue.fuel.fuelprice.callback

import com.engineblue.fuel.presentation.entity.FuelProductDisplayModel

interface SelectFuelProductListener {
    fun selectProduct(product: FuelProductDisplayModel)
}