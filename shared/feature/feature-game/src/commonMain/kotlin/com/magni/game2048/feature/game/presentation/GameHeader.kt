package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.magni.game2048.core.domain.entity.Game

@Composable
fun GameHeader(gameState: Game?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ScoreCard(
            title = "Score",
            value = gameState?.score ?: 0
        )
        ScoreCard(
            title = "Best",
            value = 0
        )
        ScoreCard(
            title = "Max Tile",
            value = gameState?.maxTile ?: 0
        )
    }
}