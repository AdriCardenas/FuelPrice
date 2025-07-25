package com.engineblue.fuel.fuelprice.screen

import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.engineblue.fuel.fuelprice.core.components.FuelTopAppBar
import com.engineblue.fuel.presentation.entity.CityStationUiState
import com.engineblue.fuel.presentation.viewmodel.CityStationViewModel
import com.fuel.engineblue.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelStationDetailScreen(
    station: String?,
    city: String?,
    latitude: Double?,
    longitude: Double?,
    navigateToMaps: (location: Location, name: String) -> Unit,
    onBack: () -> Unit
) {
    val viewModel: CityStationViewModel = koinViewModel()

    viewModel.loadStation(city!!, station!!, latitude ?: 0.0, longitude ?: 0.0)

//    fuelStation: StationDisplayModel
    Scaffold(
        topBar = {
            FuelTopAppBar(
                stringResource(R.string.fuel_station_title),
                onBackPressed = onBack,
                hasBack = true
            )
        }
    ) { paddingValues ->
        CityStationContent(
            paddingValues,
            viewModel.stationCity.collectAsState().value,
            navigateToMaps
        )
    }
}

@Composable
private fun CityStationContent(
    paddingValues: PaddingValues,
    state: CityStationUiState,
    navigateToMaps: (Location, String) -> Unit
) {
    if (state is CityStationUiState.Success)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

        ) {
            // Station Name and Location
            Text(
                text = state.selectedStation.name ?: "",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 4.dp, start = 16.dp, end = 16.dp),
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                text = "${state.selectedStation.address}",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Distancia en linea recta ${
                    formatDistance(
                        distance = state.selectedStation.distance?.div(
                            1000
                        )
                    )
                } km",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            )

            OutlinedButton(
                onClick = {
                    navigateToMaps(
                        state.selectedStation.location!!,
                        state.selectedStation.name!!
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Place,
                    contentDescription = "Ubicación en Google Maps",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("Ver en Google Maps")
            }

            // Current Fuel Prices
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (!state.selectedStation.gasPrice95.isNullOrEmpty()) {
                        Text(text = "Gasolina 95", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "${state.selectedStation.gasPrice95}€/L",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    if (!state.selectedStation.gasPrice98.isNullOrEmpty()) {
                        Text(text = "Gasolina 98", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "${state.selectedStation.gasPrice98}€/L",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    if (!state.selectedStation.dieselPrice.isNullOrEmpty()) {
                        Text(text = "Diesel", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "${state.selectedStation.dieselPrice}€/L",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    if (!state.selectedStation.dieselPremiumPrice.isNullOrEmpty()) {
                        Text(text = "Diesel Premium", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "${state.selectedStation.dieselPremiumPrice}€/L",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    if (!state.selectedStation.dieselBPrice.isNullOrEmpty()) {
                        Text(text = "Diesel B", style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "${state.selectedStation.dieselBPrice}€/L",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

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
                    .padding(vertical = 8.dp, horizontal = 16.dp),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Horario de Apertura",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "${state.selectedStation.schedule}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            Text(
                "Otras gasolineras cercanas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            ) {
                itemsIndexed(state.nearStations) { index, station ->
                    FuelStationSmallCard(
                        name = station.name ?: "",
                        distance = "${formatDistance(distance = station.distance?.div(1000))} km",
                        priceGas95 = station.gasPrice95 ?: "",
                        priceDiesel = station.dieselPrice ?: "",
                        priceDieselPremium = station.dieselPremiumPrice ?: "",
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = if (index == state.nearStations.size - 1) 16.dp else 0.dp
                        )
                    )
                }
            }
        }
}

@Composable
fun FuelStationSmallCard(
    name: String,
    distance: String,
    priceGas95: String,
    priceDiesel: String,
    priceDieselPremium: String,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp), // Adjust width as needed
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary // Or a color that fits your brand
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Distancia: $distance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (priceGas95.isNotBlank())
                    Text(
                        text = "Gasolina 95: $priceGas95 €/L",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                if (priceDiesel.isNotBlank())
                    Text(
                        text = "Diesel: $priceDiesel €/L",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                if (priceDieselPremium.isNotBlank())
                    Text(
                        text = "Diesel Premium: $priceDieselPremium €/L",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )
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
            "",
            "",
            0.0,
            0.0,
            { _, _ -> },
            {}
        )
    }
}