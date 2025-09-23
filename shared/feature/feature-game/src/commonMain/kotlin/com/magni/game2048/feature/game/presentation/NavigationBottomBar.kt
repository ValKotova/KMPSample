package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NavigationBottomBar(gameStateHolder: GameStateHolder) {
    BottomAppBar {
        IconButton(
            onClick = { gameStateHolder.navigateBack() },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                Text("Back", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}