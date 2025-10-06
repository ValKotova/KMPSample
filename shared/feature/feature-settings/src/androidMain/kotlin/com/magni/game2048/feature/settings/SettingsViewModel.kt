package com.magni.game2048.feature.settings

import androidx.lifecycle.ViewModel
import com.magni.game2048.core.domain.entity.AppSettings
import com.magni.game2048.feature.settings.presentation.SettingsStateHolder
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(
    val settingsStateHolder: SettingsStateHolder
) : ViewModel() {
    val settings: StateFlow<AppSettings?> = settingsStateHolder.settings

    fun updateSettings(settings: AppSettings) = settingsStateHolder.updateSettings(settings)

    override fun onCleared() {
        settingsStateHolder.clear()
        super.onCleared()
    }
}
