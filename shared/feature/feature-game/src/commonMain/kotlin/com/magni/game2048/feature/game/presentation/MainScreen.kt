package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.magni.game2048.core.presentation.GameColors
import com.magni.game2048.feature.game.common.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    gameStateHolder: GameStateHolder,
    gameColors: GameColors,
    modifier: Modifier = Modifier
) {
    val navigator = gameStateHolder.navigator
    val currentScreen by navigator.currentScreen.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("2048") },
                actions = {
                    IconButton(onClick = { gameStateHolder.navigateTo(Screen.Settings) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                    IconButton(onClick = { gameStateHolder.navigateTo(Screen.Records) }) {
                        Icon(Icons.Default.List, contentDescription = "Records")
                    }
                }
            )
        },
        bottomBar = {
            when (currentScreen) {
                is Screen.Game -> GameBottomBar(gameStateHolder)
                else -> NavigationBottomBar(gameStateHolder)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                is Screen.Game -> GameScreen(gameStateHolder, gameColors)
                is Screen.Settings -> Box(Modifier) /*SettingsScreen(gameStateHolder)*/
                is Screen.Records -> Box(Modifier) /*RecordsScreen(gameStateHolder)*/
            }
        }
    }
}
