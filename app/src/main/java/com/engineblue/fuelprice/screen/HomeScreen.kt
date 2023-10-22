package com.engineblue.fuelprice.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.core.components.FuelTopAppBar
import com.engineblue.fuelprice.core.ui.colorCheap
import com.engineblue.fuelprice.core.ui.colorExpensive
import com.engineblue.fuelprice.core.ui.colorRegular
import com.engineblue.presentation.entity.StationDisplayModel
import com.engineblue.presentation.viewmodel.ListStationsViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ListStationsViewModel,
    onSettingClicked: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            FuelTopAppBar(
                stringResource(R.string.fuel_price_title),
                uiState.selectedFuel.name ?: ""
            ) { onSettingClicked() }
        },
        content = { padding ->
            if (uiState.loading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier.padding(padding),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(uiState.items, itemContent = {
                        StationItem(it)
                    })
                }
            }
        }
    )
}


@Composable
fun StationItem(station: StationDisplayModel) {
    val priceColor = when (station.priceStatus) {
        StationDisplayModel.PriceStatus.CHEAP -> colorCheap
        StationDisplayModel.PriceStatus.EXPENSIVE -> colorExpensive
        else -> colorRegular
    }

    Row {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.padding(
                            start = 12.dp,
                            end = 12.dp,
                            top = 12.dp,
                            bottom = 8.dp
                        ),
                        text = "${station.price}€/L",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (station.priceStatus != StationDisplayModel.PriceStatus.UNASSIGNED)
                        Box(
                            modifier = Modifier
                                .size(48.dp, height = 6.dp)
                                .background(
                                    color = priceColor,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        )
                }
            }
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "${formatDistance(distance = station.distance?.div(1000))} km"
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(station.name)
                    }
                    if (station.city != null)
                        append(" - " + station.city)
                },
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = station.address ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private fun formatDistance(distance: Float?): String {
    if (distance == null) return "?"

    return try {
        val precision = DecimalFormat("0.0")
        precision.format(distance)
    } catch (e: NumberFormatException) {
        "?"
    }
}
