package com.magni.game2048.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.magni.game2048.feature.settings.presentation.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AndroidSettingsScreen(modifier: Modifier = Modifier) {
    val settingsViewModel: SettingsViewModel = koinViewModel()
    SettingsScreen(
        settingsStateHolder = settingsViewModel.settingsStateHolder,
        modifier = modifier
    )
}