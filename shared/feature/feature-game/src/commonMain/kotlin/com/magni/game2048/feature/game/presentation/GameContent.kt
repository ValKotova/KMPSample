package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.magni.game2048.core.domain.entity.Animation
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.presentation.GameColors
import com.magni.game2048.feature.game.presentation.GameBoard.GameBoard

@Composable
fun GameContent(
    gameState: Game?,
    animations: List<Animation>,
    gameStateHolder: GameStateHolder,
    gameColors: GameColors,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(modifier = modifier.fillMaxSize()) {
        if (gameState != null) {
            GameHeader(gameState)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .background(colorScheme.background)
        ) {
            if (gameState != null) {
                GameBoard(
                    grid = gameState.grid,
                    animations = animations,
                    onSwipe = { direction ->
                        gameStateHolder.makeMove(direction)
                    },
                    gameColors = gameColors,
                    modifier = Modifier.fillMaxSize()
                )

                if (gameState.isGameOver) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorScheme.scrim.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Game Over",
                            style = MaterialTheme.typography.headlineMedium,
                            color = colorScheme.onSurface,
                            modifier = Modifier
                                .background(
                                    color = colorScheme.surface,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(16.dp)
                        )
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = gameColors.primary
                )
            }
        }
    }
}
