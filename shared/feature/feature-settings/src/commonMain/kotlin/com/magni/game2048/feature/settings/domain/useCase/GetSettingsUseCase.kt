package com.magni.game2048.feature.settings.domain.useCase

import com.magni.game2048.core.domain.entity.AppSettings
import com.magni.game2048.core.domain.repository.SettingsRepository

class GetSettingsUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(): AppSettings = settingsRepository.loadSettings()
}