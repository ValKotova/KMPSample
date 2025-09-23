package com.magni.game2048.feature.settings.data

import com.magni.game2048.feature.settings.Theme
import com.magni.game2048.core.domain.entity.Difficulty
import com.magni.game2048.feature.settings.entity.GameSettings
import com.magni.game2048.feature.settings.repo.SettingsRepository

class SettingsRepositoryImpl : SettingsRepository {
    private var settings = GameSettings(
        playerName = "Anonymous",
        difficulty = Difficulty.MEDIUM,
        enableSound = false,
        enableMusic = false,
        theme = Theme.SYSTEM,
    )

    override suspend fun saveSettings(settings: GameSettings) {
        this.settings = settings
    }

    override suspend fun loadSettings(): GameSettings {
        return settings
    }
}