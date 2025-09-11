package com.magni.game2048.core.domain

import com.magni.game2048.feature.settings.Difficulty
import com.magni.game2048.feature.settings.GameSettings
import com.magni.game2048.feature.settings.Theme
import com.magni.game2048.feature.settings.SettingsRepository

class MockSettingsRepository : com.magni.game2048.feature.settings.SettingsRepository {
    private var settings = com.magni.game2048.feature.settings.GameSettings(
        playerName = "Test Player",
        difficulty = com.magni.game2048.feature.settings.Difficulty.MEDIUM,
        enableSound = true,
        enableMusic = true,
        theme = com.magni.game2048.feature.settings.Theme.SYSTEM,
        gridSize = 4
    )

    override suspend fun saveSettings(settings: com.magni.game2048.feature.settings.GameSettings) {
        this.settings = settings
    }

    override suspend fun loadSettings(): com.magni.game2048.feature.settings.GameSettings = settings
}