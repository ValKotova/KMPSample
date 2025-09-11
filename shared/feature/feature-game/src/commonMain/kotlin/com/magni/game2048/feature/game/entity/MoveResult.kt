package com.magni.game2048.feature.game.entity

data class MoveResult(
    val newGrid: Grid,
    val scoreDelta: Int,
    val animations: List<Animation>
)