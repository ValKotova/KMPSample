package com.magni.game2048.feature.game.presentation.GameBoard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import com.magni.game2048.core.presentation.GameColors

@Composable
fun DrawGridLines(
    gridSize: Int,
    cellSize: Dp,
    gameColors: GameColors
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val cellSizePx = size.width / gridSize

        // Draw grid lines
        for (i in 0..gridSize) {
            // Vertical lines
            drawLine(
                color = gameColors.gridLine,
                start = Offset(i * cellSizePx, 0f),
                end = Offset(i * cellSizePx, size.height),
                strokeWidth = 2f
            )
            // Horizontal lines
            drawLine(
                color = gameColors.gridLine,
                start = Offset(0f, i * cellSizePx),
                end = Offset(size.width, i * cellSizePx),
                strokeWidth = 2f
            )
        }
    }
}