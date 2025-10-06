package com.magni.game2048.core.data

import com.magni.game2048.core.domain.entity.AppSettings
import com.magni.game2048.core.domain.entity.AppTheme
import com.magni.game2048.core.domain.entity.Difficulty
import com.magni.game2048.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InMemorySettingsRepository : SettingsRepository {
    private var settings = AppSettings(
        playerName = "Player",
        difficulty = Difficulty.MEDIUM,
        soundEnabled = true,
        musicEnabled = true,
        theme = AppTheme.SYSTEM,
        gridSize = 4
    )

    private val _settingsFlow = MutableStateFlow(settings)

    override suspend fun loadSettings(): AppSettings = settings

    override suspend fun saveSettings(newSettings: AppSettings) {
        settings = newSettings
        _settingsFlow.emit(settings)
    }

    override fun getSettingsFlow(): StateFlow<AppSettings> = _settingsFlow
}