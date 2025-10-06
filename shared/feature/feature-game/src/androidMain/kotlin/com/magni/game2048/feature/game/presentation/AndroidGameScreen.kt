package com.magni.game2048.feature.game.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun AndroidGameScreen(
    modifier: Modifier = Modifier
) {
    val gameViewModel: GameViewModel = koinViewModel()
    GameScreen(
        gameStateHolder = gameViewModel.gameStateHolder,
        modifier = modifier
    )
}