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
import com.magni.game2048.core.domain.entity.Direction
import com.magni.game2048.feature.game.presentation.GameBoard.toDomain
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

                    val minSwipeDistance = 20f

                    if (abs(deltaX) > minSwipeDistance || abs(deltaY) > minSwipeDistance) {
                        if (abs(deltaX) > abs(deltaY)) {
                            val direction = if (deltaX > 0) Direction.RIGHT else Direction.LEFT
                            AnimationLogger.log(message = "Horizontal swipe: $direction (deltaX: $deltaX)")
                            onSwipe(direction.toDomain())
                        } else {
                            val direction = if (deltaY > 0) Direction.DOWN else Direction.UP
                            AnimationLogger.log(message = "Vertical swipe: $direction (deltaY: $deltaY)")
                            onSwipe(direction.toDomain())
                        }
                        start = null
                    }
                }
            } while (event.changes.any { it.pressed })
        }
    }
}