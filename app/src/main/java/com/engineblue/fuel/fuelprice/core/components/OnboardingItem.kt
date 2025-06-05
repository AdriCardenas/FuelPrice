package com.engineblue.fuel.fuelprice.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.engineblue.fuel.presentation.entity.OnBoardingItemDisplayModel

@Composable
fun OnboardingItem(
    modifier: Modifier = Modifier,
    item: com.engineblue.fuel.presentation.entity.OnBoardingItemDisplayModel,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = item.imageResource), contentDescription = item.title)
        Text(
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
            text = item.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 0.dp),
            text = item.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}