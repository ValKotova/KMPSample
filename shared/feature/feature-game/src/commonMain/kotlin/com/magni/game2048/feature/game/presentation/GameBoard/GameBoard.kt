package com.magni.game2048.feature.game.presentation.GameBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.magni.game2048.core.presentation.GameColors
import com.magni.game2048.core.domain.entity.Animation
import com.magni.game2048.core.domain.entity.Direction
import com.magni.game2048.core.domain.entity.Grid
import com.magni.game2048.feature.game.presentation.swipeable

@Composable
fun GameBoard(
    grid: Grid,
    animations: List<Animation>,
    onSwipe: (Direction) -> Unit,
    gameColors: GameColors,
    modifier: Modifier = Modifier
) {
    val animationStateHolder = rememberAnimationStateHolder()
    var displayGrid by remember { mutableStateOf(grid) }
    var isAnimating by remember { mutableStateOf(false) }

    LaunchedEffect(grid, animations) {

        if (animations.isEmpty() && !isAnimating) {
            displayGrid = grid
        } else if (animations.isNotEmpty()) {
            isAnimating = true
        }
    }

    LaunchedEffect(animationStateHolder.animationState) {
        val hasActiveOffsets = animationStateHolder.animationState.offsets.isNotEmpty()
        val hasActiveScales = animationStateHolder.animationState.scales.values.any { it != 1f }
        val shouldBeAnimating = hasActiveOffsets || hasActiveScales

        //println("GAME_DEBUG: AnimationState - offsets: ${animationStateHolder.animationState.offsets.size}, scales: ${animationStateHolder.animationState.scales.size}, shouldBeAnimating: $shouldBeAnimating, isAnimating: $isAnimating")

        if (isAnimating && !shouldBeAnimating) {
            isAnimating = false
            displayGrid = grid
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
            animationStateHolder = animationStateHolder,
            onAnimationCompleted = {
                isAnimating = false
                displayGrid = grid
                animationStateHolder.clear()
            }
        )

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
                textStyle = MaterialTheme.typography.titleLarge,
                density = LocalDensity.current
            )
        }

        Box(
            modifier = Modifier
                .size(squareSize)
                .align(Alignment.Center)
                .swipeable(onSwipe = {
                    animationStateHolder.clear()
                    onSwipe(it)
                })
        )
    }
}