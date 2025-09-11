package com.magni.game2048.core.presentation

import androidx.compose.ui.graphics.Color

data class GameColors(
    val background: Color,
    val surface: Color,
    val onSurface: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val gridLine: Color,
    val tileColors: Map<Int, Color>,
    val textColors: Map<Int, Color>
)
