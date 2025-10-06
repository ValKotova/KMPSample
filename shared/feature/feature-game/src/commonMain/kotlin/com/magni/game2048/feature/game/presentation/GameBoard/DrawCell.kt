package com.magni.game2048.feature.game.presentation.GameBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import com.magni.game2048.core.presentation.GameColors
import com.magni.game2048.core.domain.entity.Cell
import kotlin.collections.get

@Composable
fun DrawCell(
    cell: Cell,
    cellSize: Dp,
    cellPadding: Dp,
    offset: Offset,
    scale: Float,
    gameColors: GameColors,
    textStyle: TextStyle,
    density: Density
) {
    val cellSizePx = with(density) { cellSize.toPx() }
    val paddingPx = with(density) { cellPadding.toPx() }

    // Use domain coordinates directly (no swapping)
    val baseX = cell.position.x * cellSizePx
    val baseY = cell.position.y * cellSizePx

    Box(
        modifier = Modifier
            .size(cellSize)
            .offset(
                x = with(density) { (baseX + offset.x).toDp() },
                y = with(density) { (baseY + offset.y).toDp() }
            )
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .padding(cellPadding)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = gameColors.tileColors[cell.value] ?: gameColors.primary,
                    shape = CircleShape
                )
                .align(Alignment.Center)
        ) {
            Text(
                text = cell.value.toString(),
                color = gameColors.textColors[cell.value] ?: gameColors.onPrimary,
                style = textStyle,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}