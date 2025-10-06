package com.magni.game2048.core.presentation

import com.magni.game2048.core.domain.entity.AppSettings
import com.magni.game2048.core.domain.entity.AppTheme
import com.magni.game2048.core.domain.useCase.GetSettingsFlowUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedAppState(
    private val getSettingsFlowUseCase: GetSettingsFlowUseCase
) {
    private val _currentTheme = MutableStateFlow(AppTheme.SYSTEM)
    val currentTheme: StateFlow<AppTheme> = _currentTheme.asStateFlow()

    fun startObservingSettings(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            getSettingsFlowUseCase().collect { settings ->
                _currentTheme.value = settings.theme
            }
        }
    }
}