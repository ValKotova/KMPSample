package com.magni.game2048.feature.game.entity

data class Grid(
    val size: Int,
    val cells: List<List<Cell>>
)