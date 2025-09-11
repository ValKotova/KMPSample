package com.magni.game2048.feature.game.entity

import com.magni.game2048.feature.settings.entity.Difficulty

data class Game(
    val id: String,
    val grid: Grid,
    val score: Int,
    val maxTile: Int,
    val isGameOver: Boolean,
    val difficulty: Difficulty
)