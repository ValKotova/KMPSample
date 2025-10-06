package com.magni.game2048.com.magni.game2048

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.magni.game2048.core.domain.useCase.GetSettingsFlowUseCase
import com.magni.game2048.core.presentation.App
import com.magni.game2048.core.presentation.NavControllerImpl
import com.magni.game2048.core.presentation.SharedAppState
import com.magni.game2048.core.presentation.provideGameColors
import com.magni.game2048.di.appModule
import com.magni.game2048.di.desktopAppModule
import com.magni.game2048.feature.game.presentation.GameScreen
import com.magni.game2048.feature.game.presentation.GameStateHolder
import com.magni.game2048.feature.history.presentation.RecordsScreen
import com.magni.game2048.feature.history.presentation.RecordsStateHolder
import com.magni.game2048.feature.settings.presentation.SettingsScreen
import com.magni.game2048.feature.settings.presentation.SettingsStateHolder
import org.koin.compose.getKoin
import org.koin.core.context.startKoin

fun main() = application {
    startKoin {
        modules(appModule + desktopAppModule)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "2048 Game",
        state = WindowState(size = DpSize(400.dp, 600.dp))
    ) {
        val navController = remember { NavControllerImpl() }
        App(
            sharedAppState = SharedAppState(
                getSettingsFlowUseCase = getKoin().get<GetSettingsFlowUseCase>()
            ),
            navController = navController,
            gameScreen = {
                val gameStateHolder = getKoin().get<GameStateHolder>()
                GameScreen(
                    gameStateHolder = gameStateHolder,
                )
            },
            settingsScreen = {
                val settingsStateHolder = getKoin().get<SettingsStateHolder>()
                SettingsScreen(
                    settingsStateHolder = settingsStateHolder
                )
            },
            recordsScreen = {
                val recordsStateHolder = getKoin().get<RecordsStateHolder>()
                RecordsScreen(
                    recordsStateHolder = recordsStateHolder
                )
            }
        )
    }
}