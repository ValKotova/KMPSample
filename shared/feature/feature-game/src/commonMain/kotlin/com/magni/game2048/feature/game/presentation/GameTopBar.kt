package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.magni.game2048.core.domain.entity.Difficulty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    onSettingsClick: () -> Unit,
    onRecordsClick: () -> Unit,
    difficulty: Difficulty
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("2048")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = when (difficulty) {
                        Difficulty.EASY -> "Easy"
                        Difficulty.MEDIUM -> "Medium"
                        Difficulty.HARD -> "Hard"
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
            IconButton(onClick = onRecordsClick) {
                Icon(Icons.Default.List, contentDescription = "Records")
            }
        }
    )
}