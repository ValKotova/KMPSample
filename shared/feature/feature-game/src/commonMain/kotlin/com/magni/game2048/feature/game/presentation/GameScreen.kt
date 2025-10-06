package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.magni.game2048.core.domain.entity.Difficulty
import com.magni.game2048.core.presentation.GameColors
import com.magni.game2048.core.presentation.LocalNavController
import com.magni.game2048.core.presentation.Screen
import com.magni.game2048.core.presentation.provideGameColors

@Composable
fun GameScreen(
    gameStateHolder: GameStateHolder,
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    val gameState by gameStateHolder.gameState.collectAsState()
    val animations by gameStateHolder.animations.collectAsState()

    var showGameOverDialog by remember { mutableStateOf(false) }

    LaunchedEffect(gameState?.isGameOver) {
        if (gameState?.isGameOver == true) {
            showGameOverDialog = true
        }
    }

    LaunchedEffect(Unit) {
        gameStateHolder.clearAnimations()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            GameTopBar(
                onSettingsClick = { navController.navigateTo(Screen.Settings) },
                onRecordsClick = { navController.navigateTo(Screen.Records) },
                difficulty = gameState?.difficulty ?: Difficulty.MEDIUM
            )
        },
        bottomBar = {
            GameBottomBar(gameStateHolder)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            GameContent(
                gameState = gameState,
                animations = animations,
                gameStateHolder = gameStateHolder,
                gameColors = provideGameColors(),
                modifier = Modifier.fillMaxSize()
            )

            if (showGameOverDialog) {
                GameOverDialog(
                    onNewGame = {
                        gameStateHolder.startNewGame()
                        showGameOverDialog = false
                    },
                    onUndo = {
                        gameStateHolder.undoMove()
                        showGameOverDialog = false
                    },
                    onDismiss = {
                        showGameOverDialog = false
                    }
                )
            }
        }
    }
}