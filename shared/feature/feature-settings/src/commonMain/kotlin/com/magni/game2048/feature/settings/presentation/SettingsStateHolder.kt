package com.magni.game2048.feature.settings.presentation

import com.magni.game2048.core.domain.entity.AppSettings
import com.magni.game2048.core.domain.useCase.GetSettingsFlowUseCase
import com.magni.game2048.feature.settings.domain.useCase.GetSettingsUseCase
import com.magni.game2048.feature.settings.domain.useCase.UpdateSettingsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsStateHolder(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    getSettingsFlowUseCase: GetSettingsFlowUseCase
) {
    val settings: StateFlow<AppSettings> = getSettingsFlowUseCase()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        loadSettings()
    }

    fun updateSettings(newSettings: AppSettings) {
        coroutineScope.launch {
            updateSettingsUseCase(newSettings)
        }
    }

    private fun loadSettings() {
        coroutineScope.launch {
            getSettingsUseCase()
        }
    }

    fun clear() {
        coroutineScope.cancel()
    }
}