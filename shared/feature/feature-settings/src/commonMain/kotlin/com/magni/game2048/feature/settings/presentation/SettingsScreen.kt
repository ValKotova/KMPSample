package com.magni.game2048.feature.settings.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.magni.game2048.core.presentation.LocalNavController

@Composable
fun SettingsScreen(
    settingsStateHolder: SettingsStateHolder,
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    val settings by settingsStateHolder.settings.collectAsState()

    Scaffold(
        topBar = {
            SettingsTopBar(navController)
        }
    ) { innerPadding ->
        if (settings != null) {
            SettingsContent(
                settings = settings!!,
                onSettingsUpdate = { newSettings ->
                    settingsStateHolder.updateSettings(newSettings)
                },
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            Box(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}