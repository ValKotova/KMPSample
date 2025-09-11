package com.magni.game2048.core.domain

import com.magni.game2048.feature.settings.Difficulty
import com.magni.game2048.feature.settings.GameSettings
import com.magni.game2048.feature.settings.Theme
import com.magni.game2048.feature.settings.UpdateSettingsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UpdateSettingsUseCaseTest {
    private val settingsRepository = MockSettingsRepository()
    private val useCase =
        com.magni.game2048.feature.settings.UpdateSettingsUseCase(settingsRepository)

    @Test
    fun shouldUpdateSettingsCorrectly() {
        runBlocking {
            // Given
            val newSettings = com.magni.game2048.feature.settings.GameSettings(
                playerName = "New Player",
                difficulty = com.magni.game2048.feature.settings.Difficulty.HARD,
                enableSound = false,
                enableMusic = false,
                theme = com.magni.game2048.feature.settings.Theme.DARK,
                gridSize = 5
            )

            // When
            useCase(newSettings)

            // Then
            val savedSettings = settingsRepository.loadSettings()
            assertEquals("New Player", savedSettings.playerName)
            assertEquals(com.magni.game2048.feature.settings.Difficulty.HARD, savedSettings.difficulty)
            assertEquals(5, savedSettings.gridSize)
        }
    }
}