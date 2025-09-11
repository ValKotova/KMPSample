package com.magni.game2048.core.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

data class GameTextStyle(
    val fontSize: TextUnit,
    val fontWeight: FontWeight? = null,
    val color: Color = Color.Unspecified
)

fun createGameTextStyle(
    fontSize: TextUnit,
    fontWeight: FontWeight? = null,
    color: Color = Color.Unspecified
): GameTextStyle {
    return GameTextStyle(fontSize, fontWeight, color)
}