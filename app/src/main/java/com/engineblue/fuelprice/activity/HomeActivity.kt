package com.engineblue.fuelprice.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.engineblue.fuelprice.core.ui.AppTheme
import com.engineblue.presentation.viewmodel.RoutingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity: AppCompatActivity() {

    private val viewModel: RoutingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {

            }
        }
    }
}