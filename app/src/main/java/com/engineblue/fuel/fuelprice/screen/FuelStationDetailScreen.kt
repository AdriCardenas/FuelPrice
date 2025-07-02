package com.engineblue.fuel.fuelprice.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.engineblue.fuel.presentation.entity.StationDisplayModel
import com.fuel.engineblue.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelStationDetailScreen(fuelStation: StationDisplayModel, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.fuel_station_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Station Name and Location
            Text(
                text = fuelStation.name ?: "",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "${fuelStation.address}",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "${fuelStation.distance} km",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Current Fuel Prices
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Precios Actuales",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(text = "Diesel", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "${fuelStation.price}€/L",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            // Historic Fuel Prices (Chart) - Simplified representation
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            elevation = 4.dp
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(
//                    text = "Histórico de Precios (Gasolina 95 E5)",
//                    style = MaterialTheme.typography.h6,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//                // Simple chart representation - a real chart would use a charting library
//                HistoricPriceChart(fuelStation.historicPrices)
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = "Valores de referencia. Precios pueden variar.",
//                    style = MaterialTheme.typography.caption,
//                    color = Color.Gray
//                )
//            }
//        }

            // Schedule
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Horario de Apertura",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "${fuelStation.schedule}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
//
//@Composable
//fun HistoricPriceChart(historicPrices: SortedMap<DayOfWeek, Double>) {
//    val prices = historicPrices.values.toList()
//    if (prices.isEmpty()) {
//        Text("No hay datos históricos disponibles.")
//        return
//    }
//
//    val minPrice = prices.minOrNull() ?: 0.0
//    val maxPrice = prices.maxOrNull() ?: 0.0
//    val priceRange = if (maxPrice - minPrice == 0.0) 1.0 else maxPrice - minPrice
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp)
//            .padding(vertical = 8.dp)
//    ) {
//        val width = size.width
//        val height = size.height
//        val stepX = width / (prices.size - 1).toFloat()
//
//        val path = Path().apply {
//            prices.forEachIndexed { index, price ->
//                val x = index * stepX
//                val y = height - ((price - minPrice) / priceRange).toFloat() * height
//                if (index == 0) moveTo(x, y) else lineTo(x, y)
//            }
//        }
//        drawPath(
//            path = path,
//            color = Color.Blue,
//            style = Stroke(width = 4f)
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun PreviewFuelStationDetailScreen() {
    MaterialTheme {
        FuelStationDetailScreen(
            fuelStation = StationDisplayModel(
                distance = 10.0f,
                name = "Test Station",
                address = "Test Address",
                price = "1.50",
                schedule = "22:00"
            ), onBack = {})
    }
}