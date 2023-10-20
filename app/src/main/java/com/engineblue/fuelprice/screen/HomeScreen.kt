package com.engineblue.fuelprice.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.core.components.FuelTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, onBackPressed: () -> Unit) {
    Scaffold(
        topBar = { FuelTopAppBar(stringResource(R.string.fuel_price_title)) { onBackPressed() } },
        content = { padding ->
            Text(modifier = Modifier.padding(padding), text = "Prueba")
        }
    )
}