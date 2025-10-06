package com.magni.game2048

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.magni.game2048.core.domain.useCase.GetSettingsFlowUseCase
import com.magni.game2048.core.presentation.App
import com.magni.game2048.core.presentation.LocalNavController
import com.magni.game2048.core.presentation.My2048Theme
import com.magni.game2048.core.presentation.NavControllerImpl
import com.magni.game2048.core.presentation.SharedAppState
import com.magni.game2048.core.presentation.provideGameColors
import com.magni.game2048.feature.game.presentation.AndroidGameScreen
import com.magni.game2048.feature.history.presentation.AndroidRecordsScreen
import com.magni.game2048.feature.settings.AndroidSettingsScreen
import org.koin.compose.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = remember { NavControllerImpl() }
            val backCallback = remember {
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (!navController.navigateBack()) {
                            isEnabled = false
                            onBackPressedDispatcher.onBackPressed()
                        }
                    }
                }
            }

            DisposableEffect(Unit) {
                onBackPressedDispatcher.addCallback(backCallback)
                onDispose {
                    backCallback.remove()
                }
            }

            App(
                sharedAppState = SharedAppState(
                    getSettingsFlowUseCase = getKoin().get<GetSettingsFlowUseCase>()
                ),
                navController = navController,
                gameScreen = {
                    AndroidGameScreen()
                },
                settingsScreen = {
                    AndroidSettingsScreen()
                },
                recordsScreen = {
                    AndroidRecordsScreen()
                }
            )
        }
    }
}