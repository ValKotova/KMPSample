package com.magni.game2048.core.domain.repository

import com.magni.game2048.core.domain.entity.AppSettings
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    suspend fun loadSettings(): AppSettings
    suspend fun saveSettings(settings: AppSettings)

    fun getSettingsFlow(): StateFlow<AppSettings>
}