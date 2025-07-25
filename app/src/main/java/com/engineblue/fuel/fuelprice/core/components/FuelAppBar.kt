package com.engineblue.fuel.fuelprice.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.fuel.engineblue.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelTopAppBar(
    title: String,
    subtitle: String = "",
    onSettingClick: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    hasBack: Boolean = false,
    hasSetting: Boolean = false,
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
                if (subtitle.isNotEmpty())
                    Text(
                        text = subtitle,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall
                    )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        actions = {
            if (hasSetting)
                IconButton(onClick = onSettingClick) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = stringResource(id = R.string.setting),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
        },
        navigationIcon = {
            if (hasBack)
                IconButton(onClick = onBackPressed) {
                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
        }
    )
}