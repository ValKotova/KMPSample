package com.magni.game2048.core.domain.entity

data class Cell(
    val position: Position,
    val value: Int?,
    val id: Long
)