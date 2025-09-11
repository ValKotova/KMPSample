package com.magni.game2048.feature.game.presentation

import androidx.compose.ui.geometry.Offset

data class AnimationState(
    val offsets: Map<Long, Offset> = emptyMap(),
    val scales: Map<Long, Float> = emptyMap()
)