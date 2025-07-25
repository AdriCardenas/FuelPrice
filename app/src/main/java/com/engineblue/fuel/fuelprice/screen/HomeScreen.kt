package com.engineblue.fuel.fuelprice.screen

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.engineblue.fuel.fuelprice.core.components.FuelTopAppBar
import com.engineblue.fuel.fuelprice.core.ui.colorCheap
import com.engineblue.fuel.fuelprice.core.ui.colorExpensive
import com.engineblue.fuel.fuelprice.core.ui.colorRegular
import com.engineblue.fuel.presentation.entity.ListStationsUiState
import com.engineblue.fuel.presentation.entity.StationDisplayModel
import com.fuel.engineblue.R
import com.himanshoe.charty.line.model.LineData
import java.text.DecimalFormat

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: ListStationsUiState,
    onSettingClicked: () -> Unit,
    onStationClicked: (StationDisplayModel) -> Unit,
    getCurrentLocation: () -> Unit,
    reloadStations: () -> Unit,
) {
    val context = LocalContext.current
    var hasFineLocationPermission by rememberSaveable {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                hasFineLocationPermission = true
                getCurrentLocation()
            }
        }
    )

    LaunchedEffect(Unit) { // Runs once on initial composition
        if (!hasFineLocationPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            // No need to add COARSE if requesting FINE
        } else {
            // Permissions were already granted, or no foreground permissions needed to be asked.
            getCurrentLocation()
        }
    }

    Scaffold(
        topBar = {
            FuelTopAppBar(
                stringResource(R.string.fuel_price_title),
                uiState.selectedFuel.name ?: "",
                onSettingClick = onSettingClicked,
                hasSetting = true
            )
        },
        content = { padding ->
            if (uiState is ListStationsUiState.Loading) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState is ListStationsUiState.Error) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_error),
                            contentDescription = stringResource(R.string.error),
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            stringResource(R.string.no_data_available),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        OutlinedButton(
                            onClick = { reloadStations() },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(stringResource(R.string.reload))
                        }
                    }
                }
            }

            else if (uiState is ListStationsUiState.Success) {
                LazyColumn(
                    modifier.padding(padding),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(uiState.items, itemContent = {
                        StationItem(it) { item ->
//                            viewModel.loadHistoric(item)
                            onStationClicked(it)
//                            if (item.location != null && item.name != null)
//                                navigateToMaps(item.location, item.name)
                        }
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

    val pointsData = arrayListOf<LineData>()

    station.historic.forEach { historicStation ->
        pointsData.add(
            LineData(
                xValue = historicStation.date,
                yValue = historicStation.prize.replace(",", ".").toFloat()
            )
        )
    }

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
//            if (expanded && pointsData.isNotEmpty())
//                TODO AÑADIR DETALLE DE ESTACIÓN
//                LineChart(
//                    modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 120.dp).padding(16.dp),
//                    data = { pointsData },
//                    labelConfig = LabelConfig(
//                        textColor = ChartColor.Solid(MaterialTheme.colorScheme.primary),
//                        showXLabel = true,
//                        showYLabel = true,
//                        xAxisCharCount = 5,
//                        labelTextStyle = MaterialTheme.typography.labelSmall
//                    ),
//
//                    onClick = { lineData -> println("Clicked: ${lineData.xValue} -> ${lineData.yValue}") }
//                )
        }
    }
}

fun formatDistance(distance: Float?): String {
    if (distance == null) return "?"

    return try {
        val precision = DecimalFormat("0.0")
        precision.format(distance)
    } catch (e: NumberFormatException) {
        "?"
    }
}
