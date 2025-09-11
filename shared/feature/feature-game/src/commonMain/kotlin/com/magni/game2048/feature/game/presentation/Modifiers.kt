package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import com.magni.game2048.feature.game.AnimationLogger
import com.magni.game2048.feature.game.entity.Direction
import kotlin.math.abs


fun Modifier.swipeable(onSwipe: (Direction) -> Unit): Modifier = composed {
    var start by remember { mutableStateOf<Offset?>(null) }

    this.pointerInput(Unit) {
        awaitEachGesture {
            val down = awaitFirstDown()
            start = down.position
            AnimationLogger.log(message = "Swipe started at: $start")

            do {
                val event = awaitPointerEvent()
                val current = event.changes[0].position
                val startPos = start

                if (startPos != null) {
                    val deltaX = current.x - startPos.x
                    val deltaY = current.y - startPos.y

                    // Check if it's a valid swipe (minimum distance)
                    val minSwipeDistance = 20f // Adjust as needed

                    if (abs(deltaX) > minSwipeDistance || abs(deltaY) > minSwipeDistance) {
                        // Determine primary direction
                        if (abs(deltaX) > abs(deltaY)) {
                            // Horizontal swipe
                            val direction = if (deltaX > 0) Direction.RIGHT else Direction.LEFT
                            AnimationLogger.log(message = "Horizontal swipe: $direction (deltaX: $deltaX)")
                            onSwipe(direction.toDomain())
                        } else {
                            // Vertical swipe
                            val direction = if (deltaY > 0) Direction.DOWN else Direction.UP
                            AnimationLogger.log(message = "Vertical swipe: $direction (deltaY: $deltaY)")
                            onSwipe(direction.toDomain())
                        }
                        start = null // Reset after handling
                    }
                }
            } while (event.changes.any { it.pressed })
        }
    }
}