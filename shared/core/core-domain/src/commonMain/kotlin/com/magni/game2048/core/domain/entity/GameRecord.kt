package com.magni.game2048.core.domain.entity

data class GameRecord(
    val id: String,
    val score: Int,
    val maxTile: Int,
    val date: Long,
    val difficulty: Difficulty,
    val playerName: String
)