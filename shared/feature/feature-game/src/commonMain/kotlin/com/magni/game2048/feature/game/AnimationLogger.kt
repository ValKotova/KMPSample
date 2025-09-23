package com.magni.game2048.feature.game

import com.magni.game2048.core.domain.entity.Position

object AnimationLogger {
    private const val ENABLED = true
    private const val LOG_LEVEL = 2 // 1=Basic, 2=Detailed, 3=Verbose

    fun log(level: Int = 1, message: String) {
        if (ENABLED && level <= LOG_LEVEL) {
            println("ANIMATION_DEBUG: $message")
        }
    }

    fun logPosition(level: Int = 2, label: String, position: Position, cellSizePx: Float) {
        if (ENABLED && level <= LOG_LEVEL) {
            val x = position.y * cellSizePx
            val y = position.x * cellSizePx
            log(level, "$label: Grid(${position.x}, ${position.y}) -> Pixel($x, $y)")
        }
    }
}