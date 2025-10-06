package com.magni.game2048.core.domain.entity

data class AppSettings(
    val playerName: String,
    val difficulty: Difficulty,
    val soundEnabled: Boolean,
    val musicEnabled: Boolean,
    val theme: AppTheme,
    val gridSize: Int
)