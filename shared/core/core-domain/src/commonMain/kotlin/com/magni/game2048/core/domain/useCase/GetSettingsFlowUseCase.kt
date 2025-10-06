package com.magni.game2048.core.domain.useCase

import com.magni.game2048.core.domain.entity.AppSettings
import com.magni.game2048.core.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.StateFlow

class GetSettingsFlowUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): StateFlow<AppSettings> = settingsRepository.getSettingsFlow()
}