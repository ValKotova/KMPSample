package com.magni.game2048.core.domain.entity

data class MoveResult(
    val newGrid: Grid,
    val scoreDelta: Int,
    val animations: List<Animation>
)