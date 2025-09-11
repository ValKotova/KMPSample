package com.magni.game2048.feature.game.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.magni.game2048.core.presentation.GameColors
import com.magni.game2048.feature.game.entity.Direction

fun Direction.toDomain(): Direction {
    return when (this) {
        Direction.LEFT -> Direction.UP
        Direction.RIGHT -> Direction.DOWN
        Direction.UP -> Direction.LEFT
        Direction.DOWN -> Direction.RIGHT
    }
}