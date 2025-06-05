package com.engineblue.fuel.fuelprice.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.fuel.engineblue.R
import com.engineblue.fuel.presentation.entity.FuelProductDisplayModel
import com.engineblue.fuel.presentation.viewmodel.FuelViewModel
import org.koin.androidx.compose.get

@Composable
fun ConfigurationFuelScreen(modifier: Modifier = Modifier, onProductSelected: () -> Unit) {

    val viewModel: FuelViewModel = get()

    val listOfFuels = viewModel.fuelProductList.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 64.dp),
            text = stringResource(id = R.string.select_your_preferred_fuel),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.your_app_for_fuel_control),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(listOfFuels.value, itemContent = {
                FuelItem(it) { fuel ->
                    viewModel.saveProduct(fuel.id, fuel.name)
                    onProductSelected()
                }
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FuelItem(
    fuelProduct: FuelProductDisplayModel, onSelectProduct: (fuel: FuelProductDisplayModel) -> Unit
) {
    Card(onClick = { onSelectProduct(fuelProduct) }) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(fuelProduct.name)
                }
                if (fuelProduct.nameAbbreviature != null) {
                    append(" - ")
                    append(fuelProduct.nameAbbreviature)
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center
        )
    }
}