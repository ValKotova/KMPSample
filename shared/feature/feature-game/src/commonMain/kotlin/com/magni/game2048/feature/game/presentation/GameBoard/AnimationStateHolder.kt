package com.magni.game2048.feature.game.presentation.GameBoard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import com.magni.game2048.feature.game.AnimationLogger

@Stable
class AnimationStateHolder {
    private val _offsets = mutableStateOf<Map<Long, Offset>>(emptyMap())
    private val _scales = mutableStateOf<Map<Long, Float>>(emptyMap())

    val animationState: AnimationState
        get() = AnimationState(offsets = _offsets.value, scales = _scales.value)

    fun updateBatch(newOffsets: Map<Long, Offset>, newScales: Map<Long, Float>) {

        _offsets.value = _offsets.value.toMutableMap().apply {
            putAll(newOffsets)
        }
        _scales.value = _scales.value.toMutableMap().apply {
            putAll(newScales)
        }
    }

    fun clear() {
        _offsets.value = emptyMap()
        _scales.value = emptyMap()
    }
}

@Composable
fun rememberAnimationStateHolder(): AnimationStateHolder {
    return remember { AnimationStateHolder() }
}