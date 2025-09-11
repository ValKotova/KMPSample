package com.magni.game2048.feature.settings.repo

import com.magni.game2048.feature.settings.entity.GameSettings

interface SettingsRepository {
    suspend fun saveSettings(settings: GameSettings)
    suspend fun loadSettings(): GameSettings
}