package com.magni.game2048.feature.game.presentation

import androidx.lifecycle.ViewModel
import com.magni.game2048.core.domain.entity.Animation
import com.magni.game2048.core.domain.entity.Direction
import com.magni.game2048.core.domain.entity.Game
import kotlinx.coroutines.flow.StateFlow

class GameViewModel(
    val gameStateHolder: GameStateHolder
) : ViewModel() {
    val gameState: StateFlow<Game?> = gameStateHolder.gameState
    val animations: StateFlow<List<Animation>> = gameStateHolder.animations

    fun makeMove(direction: Direction) = gameStateHolder.makeMove(direction)
    fun startNewGame() = gameStateHolder.startNewGame()
    fun undoMove() = gameStateHolder.undoMove()

    override fun onCleared() {
        gameStateHolder.clear()
        super.onCleared()
    }
}