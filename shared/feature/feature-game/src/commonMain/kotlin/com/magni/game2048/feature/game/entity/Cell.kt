package com.magni.game2048.feature.game.entity

data class Cell(
    val position: Position,
    val value: Int?,
    val id: Long
)