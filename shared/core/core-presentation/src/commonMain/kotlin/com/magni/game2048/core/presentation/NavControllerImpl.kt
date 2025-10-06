package com.magni.game2048.core.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavControllerImpl {
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Game)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val backStack = mutableListOf<Screen>()

    fun navigateTo(screen: Screen) {
        backStack.add(_currentScreen.value)
        _currentScreen.value = screen
    }

    fun navigateBack(): Boolean {
        return if (backStack.isNotEmpty()) {
            _currentScreen.value = backStack.removeAt(backStack.size - 1)
            true
        } else {
            false
        }
    }

    fun canGoBack(): Boolean = backStack.isNotEmpty()
}