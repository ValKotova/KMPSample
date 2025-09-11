package com.magni.game2048.core.domain

import com.magni.game2048.feature.settings.Difficulty
import com.magni.game2048.feature.settings.GameSettings
import com.magni.game2048.feature.settings.Theme
import com.magni.game2048.feature.settings.GetSettingsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetSettingsUseCaseTest {
    private val settingsRepository = MockSettingsRepository()
    private val useCase = com.magni.game2048.feature.settings.GetSettingsUseCase(settingsRepository)

    @Test
    fun shouldReturnCurrentSettings() {
        runBlocking {
            // Given
            val expectedSettings = com.magni.game2048.feature.settings.GameSettings(
                playerName = "Test Player",
                difficulty = com.magni.game2048.feature.settings.Difficulty.MEDIUM,
                enableSound = true,
                enableMusic = true,
                theme = com.magni.game2048.feature.settings.Theme.SYSTEM,
                gridSize = 4
            )

            // When
            val result = useCase()

            // Then
            assertEquals(expectedSettings.playerName, result.playerName)
            assertEquals(expectedSettings.difficulty, result.difficulty)
            assertEquals(expectedSettings.gridSize, result.gridSize)
        }
    }
}