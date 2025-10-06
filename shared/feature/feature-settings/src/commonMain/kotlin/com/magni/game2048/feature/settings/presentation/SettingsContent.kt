package com.magni.game2048.feature.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.magni.game2048.core.domain.entity.AppSettings
import com.magni.game2048.core.domain.entity.AppTheme
import com.magni.game2048.core.domain.entity.Difficulty

@Composable
fun SettingsContent(
    settings: AppSettings,
    onSettingsUpdate: (AppSettings) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Player Settings",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            OutlinedTextField(
                value = settings.playerName,
                onValueChange = { newName ->
                    onSettingsUpdate(settings.copy(playerName = newName))
                },
                label = { Text("Player Name") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text(
                "Game Settings",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            Text("Difficulty", style = MaterialTheme.typography.bodyMedium)
            Column {
                Difficulty.entries.forEach { difficulty ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSettingsUpdate(settings.copy(difficulty = difficulty))
                            }
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = settings.difficulty == difficulty,
                            onClick = {
                                onSettingsUpdate(settings.copy(difficulty = difficulty))
                            }
                        )
                        Text(
                            text = when (difficulty) {
                                Difficulty.EASY -> "Easy"
                                Difficulty.MEDIUM -> "Medium"
                                Difficulty.HARD -> "Hard"
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                value = settings.gridSize.toString(),
                onValueChange = { newSize ->
                    val size = newSize.toIntOrNull() ?: settings.gridSize
                    if (size in 3..10) {
                        onSettingsUpdate(settings.copy(gridSize = size))
                    }
                },
                label = { Text("Grid Size (3-10)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Text(
                "Appearance",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            Text("Theme", style = MaterialTheme.typography.bodyMedium)
            Column {
                AppTheme.entries.forEach { theme ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSettingsUpdate(settings.copy(theme = theme))
                            }
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = settings.theme == theme,
                            onClick = {
                                onSettingsUpdate(settings.copy(theme = theme))
                            }
                        )
                        Text(
                            text = when (theme) {
                                AppTheme.SYSTEM -> "System Default"
                                AppTheme.LIGHT -> "Light"
                                AppTheme.DARK -> "Dark"
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }

        /*item {
            Text(
                "Audio",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Sound Effects")
                Switch(
                    checked = settings.soundEnabled,
                    onCheckedChange = { enabled ->
                        onSettingsUpdate(settings.copy(soundEnabled = enabled))
                    }
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Background Music")
                Switch(
                    checked = settings.musicEnabled,
                    onCheckedChange = { enabled ->
                        onSettingsUpdate(settings.copy(musicEnabled = enabled))
                    }
                )
            }
        }*/
    }
}