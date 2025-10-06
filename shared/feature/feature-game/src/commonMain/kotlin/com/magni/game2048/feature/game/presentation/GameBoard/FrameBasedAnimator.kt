package com.magni.game2048.feature.game.presentation.GameBoard

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.ui.geometry.Offset
import com.magni.game2048.feature.game.AnimationLogger

class ImprovedAnimator(private val onUpdate: (Map<Long, Offset>, Map<Long, Float>) -> Unit) {
    var isAnimating = false
    private val activeAnimations = mutableListOf<AnimationItem>()

    data class AnimationItem(
        val cellId: Long,
        val startOffset: Offset,
        val endOffset: Offset,
        val startScale: Float,
        val endScale: Float,
        val durationMs: Long,
        val startTime: Long,
        val easing: Easing = LinearEasing,
        val description: String // For debugging
    )

    fun startAnimations(animations: List<AnimationItem>) {
        activeAnimations.clear()
        activeAnimations.addAll(animations)
        isAnimating = true
    }

    suspend fun animateFrame(frameTime: Long) {
        if (!isAnimating) return

        val currentTime = frameTime / 1_000_000 // Convert to ms
        val updates = mutableMapOf<Long, Offset>()
        val scales = mutableMapOf<Long, Float>()
        var allFinished = true

        val iterator = activeAnimations.iterator()
        while (iterator.hasNext()) {
            val animation = iterator.next()
            val elapsed = currentTime - animation.startTime
            val progress = (elapsed.toFloat() / animation.durationMs).coerceIn(0f, 1f)
            val easedProgress = animation.easing.transform(progress)

            if (progress >= 1f) {
                // Animation finished
                updates[animation.cellId] = animation.endOffset
                scales[animation.cellId] = animation.endScale
                iterator.remove()
            } else {
                // Animation in progress
                allFinished = false
                val currentOffset = Offset(
                    animation.startOffset.x + (animation.endOffset.x - animation.startOffset.x) * easedProgress,
                    animation.startOffset.y + (animation.endOffset.y - animation.startOffset.y) * easedProgress
                )
                val currentScale = animation.startScale + (animation.endScale - animation.startScale) * easedProgress

                updates[animation.cellId] = currentOffset
                scales[animation.cellId] = currentScale
            }
        }

        // Apply updates
        if (updates.isNotEmpty() || scales.isNotEmpty()) {
            onUpdate(updates, scales)
        }

        if (allFinished) {
            isAnimating = false
        }
    }

    fun stop() {
        isAnimating = false
        activeAnimations.clear()
    }
}