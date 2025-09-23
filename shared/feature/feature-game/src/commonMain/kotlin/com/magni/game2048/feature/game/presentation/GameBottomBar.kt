package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GameBottomBar(gameStateHolder: GameStateHolder) {
    BottomAppBar {
        IconButton(
            onClick = { gameStateHolder.undoMove() },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Undo, contentDescription = "Undo Move")
                Text("Undo", style = MaterialTheme.typography.labelSmall)
            }
        }

        IconButton(
            onClick = { gameStateHolder.startNewGame() },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Refresh, contentDescription = "New Game")
                Text("New Game", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}