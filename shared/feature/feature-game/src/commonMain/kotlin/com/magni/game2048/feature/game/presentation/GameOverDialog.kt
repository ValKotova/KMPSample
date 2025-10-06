package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GameOverDialog(
    onNewGame: () -> Unit,
    onUndo: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Game Over!",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                "No more moves available. What would you like to do?",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            Button(
                onClick = onNewGame,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Start New Game")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onUndo,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Undo Last Move")
            }
        },
        modifier = modifier
    )
}