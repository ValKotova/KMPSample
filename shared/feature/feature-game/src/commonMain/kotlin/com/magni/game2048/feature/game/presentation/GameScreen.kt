package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.magni.game2048.core.presentation.GameColors

@Composable
fun GameScreen(
    gameStateHolder: GameStateHolder,
    gameColors: GameColors,
    modifier: Modifier = Modifier
) {
    val gameState by gameStateHolder.gameState.collectAsState()
    val animations by gameStateHolder.animations.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        GameHeader(gameState)

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            if (gameState != null) {
                GameBoard(
                    grid = gameState!!.grid,
                    animations = animations,
                    onSwipe = { direction ->
                        gameStateHolder.makeMove(direction)
                    },
                    gameColors = gameColors,
                    textStyle = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}