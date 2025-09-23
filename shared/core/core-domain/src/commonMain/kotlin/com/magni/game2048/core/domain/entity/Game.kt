package com.magni.game2048.core.domain.entity

data class Game(
    val id: String,
    val grid: Grid,
    val score: Int,
    val maxTile: Int,
    val isGameOver: Boolean,
    val difficulty: Difficulty
)