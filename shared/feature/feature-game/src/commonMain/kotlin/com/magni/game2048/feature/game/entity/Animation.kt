package com.magni.game2048.feature.game.entity

sealed interface Animation {
    data class Move(
        val from: Position,
        val to: Position,
        val value: Int,
        val cellId: Long
    ) : Animation

    data class Merge(
        val sources: List<Position>,
        val destination: Position,
        val newValue: Int,
        val newCellId: Long
    ) : Animation

    data class Appear(
        val at: Position,
        val value: Int,
        val newCell: Cell
    ) : Animation
}
