package com.magni.game2048.feature.game.presentation

import com.magni.game2048.core.domain.entity.Direction

fun Direction.toDomain(): Direction {
    return when (this) {
        Direction.LEFT -> Direction.UP
        Direction.RIGHT -> Direction.DOWN
        Direction.UP -> Direction.LEFT
        Direction.DOWN -> Direction.RIGHT
    }
}