package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.magni.game2048.core.presentation.GameColors
import com.magni.game2048.core.domain.entity.Animation
import com.magni.game2048.core.domain.entity.Direction
import com.magni.game2048.core.domain.entity.Grid

@Composable
fun GameBoard(
    grid: Grid,
    animations: List<Animation>,
    onSwipe: (Direction) -> Unit,
    gameColors: GameColors,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    val animationStateHolder = rememberAnimationStateHolder()
    var displayGrid by remember { mutableStateOf(grid) }
    var isAnimating by remember { mutableStateOf(false) }

    LaunchedEffect(animations) {
        if (animations.isNotEmpty() && !isAnimating) {
            isAnimating = true
            // Don't update displayGrid yet - wait for animations to complete
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .background(gameColors.background)
            .fillMaxSize()
    ) {
        val squareSize = min(maxWidth, maxHeight)
        val gridPadding = 16.dp
        val cellPadding = 4.dp
        val cellSize = (squareSize - gridPadding * 2) / grid.size

        HandleAnimations(
            animations = animations,
            grid = displayGrid,
            cellSize = cellSize,
            density = LocalDensity.current,
            animationStateHolder = animationStateHolder
        )

        // Draw grid background
        Box(
            modifier = Modifier
                .size(squareSize)
                .align(Alignment.Center)
                .padding(gridPadding)
                .background(gameColors.surfaceVariant)
        ) {
            DrawGridLines(
                gridSize = displayGrid.size,
                cellSize = cellSize,
                gameColors = gameColors
            )

            DrawCells(
                grid = displayGrid,
                cellSize = cellSize,
                cellPadding = cellPadding,
                animationState = animationStateHolder.animationState,
                gameColors = gameColors,
                textStyle = textStyle,
                density = LocalDensity.current
            )
        }

        // Update display grid after animations complete
        LaunchedEffect(animationStateHolder.animationState) {
            if (isAnimating && animationStateHolder.animationState.offsets.isEmpty() &&
                animationStateHolder.animationState.scales.values.all { it == 1f }) {
                displayGrid = grid
                isAnimating = false
            }
        }

        // Swipe detector
        Box(
            modifier = Modifier
                .size(squareSize)
                .align(Alignment.Center)
                .swipeable(onSwipe = onSwipe)
        )
    }
}