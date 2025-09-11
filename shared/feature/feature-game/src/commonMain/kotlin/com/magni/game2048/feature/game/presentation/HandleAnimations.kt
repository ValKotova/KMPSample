package com.magni.game2048.feature.game.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import com.magni.game2048.feature.game.AnimationLogger
import com.magni.game2048.feature.game.entity.Animation
import com.magni.game2048.feature.game.entity.Grid
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun HandleAnimations(
    animations: List<Animation>,
    grid: Grid,
    cellSize: Dp,
    density: Density,
    animationStateHolder: AnimationStateHolder
) {
    val cellSizePx = with(density) { cellSize.toPx() }
    val animator = remember {
        ImprovedAnimator { offsets, scales ->
            animationStateHolder.updateBatch(offsets, scales)
        }
    }

    LaunchedEffect(animations) {
        if (animations.isEmpty()) return@LaunchedEffect

        AnimationLogger.log(1, "Starting ${animations.size} animations")
        animationStateHolder.clear()

        val animationItems = mutableListOf<ImprovedAnimator.AnimationItem>()
        val now = System.nanoTime() / 1_000_000

        animations.forEach { animation ->
            when (animation) {
                is Animation.Move -> {
                    AnimationLogger.log(2, "Processing move: ${animation.from} -> ${animation.to}")

                    // Calculate positions - use domain coordinates directly
                    val fromX = animation.from.x * cellSizePx
                    val fromY = animation.from.y * cellSizePx
                    val toX = animation.to.x * cellSizePx
                    val toY = animation.to.y * cellSizePx

                    AnimationLogger.log(2, "Pixel positions - from: ($fromX, $fromY), to: ($toX, $toY)")

                    // Calculate distance for duration
                    val distance = sqrt((toX - fromX).pow(2) + (toY - fromY).pow(2))
                    val duration = (distance / 2).toLong().coerceIn(100, 400)

                    AnimationLogger.log(2, "Move distance: $distance, duration: $duration")

                    animationItems.add(
                        ImprovedAnimator.AnimationItem(
                            cellId = animation.cellId,
                            startOffset = Offset.Zero, // Start at current position
                            endOffset = Offset(toX - fromX, toY - fromY), // Move to new position
                            startScale = 1f,
                            endScale = 1f,
                            durationMs = duration,
                            startTime = now,
                            easing = Easing { fraction ->
                                // FastOutSlowInEasing equivalent
                                val t = fraction - 1.0f
                                t * t * t + 1.0f
                            },
                            description = "Move from ${animation.from} to ${animation.to}"
                        )
                    )
                }
                is Animation.Merge -> {
                    AnimationLogger.log(2, "Processing merge to ${animation.destination}")

                    // Calculate destination pixel position
                    val destX = animation.destination.x * cellSizePx
                    val destY = animation.destination.y * cellSizePx

                    AnimationLogger.log(2, "Merge destination pixel: ($destX, $destY)")

                    // Process source movements
                    animation.sources.forEach { sourcePos ->
                        val sourceId = grid.cells[sourcePos.x][sourcePos.y].id
                        val sourceX = sourcePos.x * cellSizePx
                        val sourceY = sourcePos.y * cellSizePx

                        AnimationLogger.log(2, "Merge source $sourcePos pixel: ($sourceX, $sourceY)")

                        // Calculate distance for duration
                        val distance = sqrt((destX - sourceX).pow(2) + (destY - sourceY).pow(2))
                        val duration = (distance / 2).toLong().coerceIn(100, 400)

                        AnimationLogger.log(2, "Merge source distance: $distance, duration: $duration")

                        animationItems.add(
                            ImprovedAnimator.AnimationItem(
                                cellId = sourceId,
                                startOffset = Offset.Zero, // Start at current position
                                endOffset = Offset(destX - sourceX, destY - sourceY), // Move to destination
                                startScale = 1f,
                                endScale = 1f,
                                durationMs = duration,
                                startTime = now,
                                easing = Easing { fraction ->
                                    val t = fraction - 1.0f
                                    t * t * t + 1.0f
                                },
                                description = "Merge source $sourcePos to ${animation.destination}"
                            )
                        )
                    }

                    // Add scale animation for new cell
                    animationItems.add(
                        ImprovedAnimator.AnimationItem(
                            cellId = animation.newCellId,
                            startOffset = Offset.Zero,
                            endOffset = Offset.Zero,
                            startScale = 0f,
                            endScale = 1f,
                            durationMs = 200,
                            startTime = now + 100, // Start after movement begins
                            easing = Easing { fraction ->
                                val t = fraction - 1.0f
                                t * t * t + 1.0f
                            },
                            description = "Merge scale for new cell at ${animation.destination}"
                        )
                    )
                }
                is Animation.Appear -> {
                    AnimationLogger.log(2, "Processing appear at ${animation.at}")

                    // Use domain coordinates directly (no swapping)
                    val atX = animation.at.x * cellSizePx
                    val atY = animation.at.y * cellSizePx

                    animationItems.add(
                        ImprovedAnimator.AnimationItem(
                            cellId = animation.newCell.id,
                            startOffset = Offset.Zero,
                            endOffset = Offset.Zero,
                            startScale = 0f,
                            endScale = 1f,
                            durationMs = 200,
                            startTime = now,
                            easing = Easing { fraction ->
                                val t = fraction - 1.0f
                                t * t * t + 1.0f
                            },
                            description = "Appear at ${animation.at}"
                        )
                    )
                }
            }
        }

        // Start animations
        animator.startAnimations(animationItems)

        // Animation loop with proper frame timing
        var lastFrameTime = System.nanoTime()
        while (animator.isAnimating) {
            val frameTime = withFrameNanos { it }
            val deltaTime = frameTime - lastFrameTime
            lastFrameTime = frameTime

            animator.animateFrame(frameTime)

            // Control frame rate to avoid excessive updates
            if (deltaTime < 16_000_000) {
                delay(1) // Yield to other coroutines
            }
        }

        AnimationLogger.log(1, "All animations completed")
        animationStateHolder.clear()
    }
}