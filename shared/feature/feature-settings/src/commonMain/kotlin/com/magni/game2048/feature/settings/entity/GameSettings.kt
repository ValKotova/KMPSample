package com.magni.game2048.feature.settings.entity

import com.magni.game2048.feature.settings.Theme

data class GameSettings(
    val playerName: String,
    val difficulty: Difficulty,
    val enableSound: Boolean,
    val enableMusic: Boolean,
    val theme: Theme,
    val gridSize: Int = 4 // Default
)