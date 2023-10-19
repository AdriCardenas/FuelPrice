package com.engineblue.fuelprice.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.engineblue.fuelprice.R
import com.engineblue.fuelprice.core.components.OnBoardNavButton
import com.engineblue.fuelprice.core.components.OnboardingItem
import com.engineblue.presentation.entity.OnBoardingItemDisplayModel


@Composable
fun OnBoardingScreen(
    onFinishOnBoarding: () -> Unit
) {
    val onboardPagesList = arrayListOf<OnBoardingItemDisplayModel>()

    onboardPagesList.add(
        OnBoardingItemDisplayModel(
            R.drawable.cost_control_boarding,
            stringResource(R.string.onboarding_cost_title),
            stringResource(R.string.onboarding_cost_description)
        )
    )

    onboardPagesList.add(
        OnBoardingItemDisplayModel(
            R.drawable.fuel_boarding,
            stringResource(R.string.onboarding_fuel_title),
            stringResource(R.string.onboarding_fuel_description)
        )
    )

    onboardPagesList.add(
        OnBoardingItemDisplayModel(
            R.drawable.secure_data_boarding,
            stringResource(R.string.onboarding_secure_title),
            stringResource(R.string.onboarding_secure_description)
        )
    )

    val currentPage = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            IconButton(onClick = { if (currentPage.value > 0) currentPage.value-- }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    stringResource(id = R.string.go_back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Box(modifier = Modifier.weight(1f))
            TextButton(
                modifier = Modifier.padding(16.dp),
                onClick = onFinishOnBoarding,
            ) {
                Text(text = stringResource(R.string.skip), style = MaterialTheme.typography.titleMedium)
            }
        }

        OnboardingItem(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            item = onboardPagesList[currentPage.value]
        )

        OnBoardNavButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp),
            currentPage = currentPage.value,
            noOfPages = onboardPagesList.size,
            onNextClicked = {
                currentPage.value++
            },
            onStartClicked = onFinishOnBoarding
        )

    }
}

