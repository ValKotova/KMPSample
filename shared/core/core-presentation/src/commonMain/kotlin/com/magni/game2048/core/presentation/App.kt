package com.magni.game2048.core.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalNavController = staticCompositionLocalOf<NavControllerImpl> {
    error("No NavController provided")
}

@Composable
fun App(
    sharedAppState: SharedAppState,
    navController: NavControllerImpl,
    gameScreen: @Composable () -> Unit,
    settingsScreen: @Composable () -> Unit,
    recordsScreen: @Composable () -> Unit
) {
    val currentScreen by navController.currentScreen.collectAsState()
    val currentTheme by sharedAppState.currentTheme.collectAsState()
    LaunchedEffect(sharedAppState) {
        sharedAppState.startObservingSettings(this)
    }

    CompositionLocalProvider(LocalNavController provides navController) {
        My2048Theme(theme = currentTheme) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                when (currentScreen) {
                    Screen.Game -> gameScreen()
                    Screen.Settings -> settingsScreen()
                    Screen.Records -> recordsScreen()
                }
            }
        }
    }
}