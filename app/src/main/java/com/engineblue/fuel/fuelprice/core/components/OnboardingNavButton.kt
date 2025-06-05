package com.engineblue.fuel.fuelprice.core.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fuel.engineblue.R

@Composable
fun OnBoardNavButton(
    modifier: Modifier = Modifier,
    currentPage: Int,
    noOfPages: Int,
    onNextClicked: () -> Unit,
    onStartClicked: () -> Unit
) {
    Button(
        onClick = {
            if (currentPage < noOfPages - 1) {
                onNextClicked()
            } else {
                onStartClicked()
            }
        }, modifier = modifier
    ) {
        Text(
            text = if (currentPage < noOfPages - 1) stringResource(R.string.next)
            else stringResource(R.string.start)
        )
    }
}