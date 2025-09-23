package com.magni.game2048.core.domain.entity

data class Grid(
    val size: Int,
    val cells: List<List<Cell>>
)