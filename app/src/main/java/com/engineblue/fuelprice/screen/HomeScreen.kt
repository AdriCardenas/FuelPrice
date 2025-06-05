package com.engineblue.fuelprice.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.core.components.FuelTopAppBar
import com.engineblue.fuelprice.core.ui.colorCheap
import com.engineblue.fuelprice.core.ui.colorExpensive
import com.engineblue.fuelprice.core.ui.colorRegular
import com.engineblue.presentation.entity.StationDisplayModel
import com.engineblue.presentation.viewmodel.ListStationsViewModel
import java.text.DecimalFormat

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
                        StationItem(it) { item -> viewModel.loadHistoric(item) }
                    })
                }
            }
        }
    )
}


@Composable
fun StationItem(station: StationDisplayModel, onClickItem: (StationDisplayModel) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val priceColor = when (station.priceStatus) {
        StationDisplayModel.PriceStatus.CHEAP -> colorCheap
        StationDisplayModel.PriceStatus.EXPENSIVE -> colorExpensive
        else -> colorRegular
    }

    val pointsData: List<Point> =
        listOf(
            Point(0f, 40f),
            Point(1f, 90f),
            Point(2f, 0f),
            Point(3f, 60f),
            Point(4f, 10f)
        )

    Box(modifier = Modifier.clickable {
        expanded = !expanded
        onClickItem(station)
    }) {
        Column {
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
            if (expanded)
                LineChart(
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    lineChartData = LineChartData(
                        linePlotData = LinePlotData(
                            lines = listOf(
                                Line(
                                    dataPoints = pointsData,
                                    LineStyle(),
                                    IntersectionPoint(),
                                    SelectionHighlightPoint(),
                                    ShadowUnderLine(),
                                    SelectionHighlightPopUp()
                                )
                            ),
                        )
                    )
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
