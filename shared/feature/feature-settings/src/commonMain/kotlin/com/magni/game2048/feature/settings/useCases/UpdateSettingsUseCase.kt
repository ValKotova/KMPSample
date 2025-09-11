package com.magni.game2048.feature.settings.useCases

import com.magni.game2048.feature.settings.entity.GameSettings
import com.magni.game2048.feature.settings.repo.SettingsRepository

class UpdateSettingsUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(settings: GameSettings) {
        settingsRepository.saveSettings(settings)
    }
}