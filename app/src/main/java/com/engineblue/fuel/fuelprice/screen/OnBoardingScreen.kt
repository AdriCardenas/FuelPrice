package com.engineblue.fuel.fuelprice.screen

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
import com.fuel.engineblue.R
import com.engineblue.fuel.fuelprice.core.components.OnBoardNavButton
import com.engineblue.fuel.fuelprice.core.components.OnboardingItem
import com.engineblue.fuel.presentation.entity.OnBoardingItemDisplayModel
import com.engineblue.fuel.presentation.viewmodel.OnBoardingViewModel
import org.koin.androidx.compose.get


@Composable
fun OnBoardingScreen(
    onFinishOnBoarding: () -> Unit,
) {
    val viewModel: com.engineblue.fuel.presentation.viewmodel.OnBoardingViewModel = get()

    val onboardPagesList = arrayListOf<com.engineblue.fuel.presentation.entity.OnBoardingItemDisplayModel>()

    onboardPagesList.add(
        com.engineblue.fuel.presentation.entity.OnBoardingItemDisplayModel(
            R.drawable.cost_control_boarding,
            stringResource(R.string.onboarding_cost_title),
            stringResource(R.string.onboarding_cost_description)
        )
    )

    onboardPagesList.add(
        com.engineblue.fuel.presentation.entity.OnBoardingItemDisplayModel(
            R.drawable.fuel_boarding,
            stringResource(R.string.onboarding_fuel_title),
            stringResource(R.string.onboarding_fuel_description)
        )
    )

    onboardPagesList.add(
        com.engineblue.fuel.presentation.entity.OnBoardingItemDisplayModel(
            R.drawable.secure_data_boarding,
            stringResource(R.string.onboarding_secure_title),
            stringResource(R.string.onboarding_secure_description)
        )
    )

    val currentPage = remember { mutableStateOf(0) }

    val prefFirstStart = stringResource(R.string.pref_first_start)

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
            if (currentPage.value > 0)
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
                onClick = {
                    viewModel.saveOnboardingChecked(prefFirstStart)
                    onFinishOnBoarding()
                },
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    style = MaterialTheme.typography.titleMedium
                )
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
            onStartClicked = {
                viewModel.saveOnboardingChecked(prefFirstStart)
                onFinishOnBoarding()
            }
        )

    }
}

