package com.magni.game2048.feature.game.presentation.GameBoard

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import com.magni.game2048.core.presentation.GameColors
import com.magni.game2048.feature.game.AnimationLogger
import com.magni.game2048.core.domain.entity.Grid

@Composable
fun DrawCells(
    grid: Grid,
    cellSize: Dp,
    cellPadding: Dp,
    animationState: AnimationState,
    gameColors: GameColors,
    textStyle: TextStyle,
    density: Density
) {
    grid.cells.forEach { row ->
        row.forEach { cell ->
            val offset = animationState.offsets[cell.id] ?: Offset.Zero
            val scale = animationState.scales[cell.id] ?: 1f

            if (cell.value != null && scale > 0.01f) {
                DrawCell(
                    cell = cell,
                    cellSize = cellSize,
                    cellPadding = cellPadding,
                    offset = offset,
                    scale = scale,
                    gameColors = gameColors,
                    textStyle = textStyle,
                    density = density
                )
            }
        }
    }
}