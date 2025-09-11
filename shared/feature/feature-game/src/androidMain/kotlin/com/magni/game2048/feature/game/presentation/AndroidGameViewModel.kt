package com.magni.game2048.feature.game.presentation

import androidx.lifecycle.ViewModel
import com.magni.game2048.feature.game.entity.Animation
import com.magni.game2048.feature.game.entity.Direction
import com.magni.game2048.feature.game.entity.Game
import kotlinx.coroutines.flow.StateFlow

class AndroidGameViewModel(
    private val gameStateHolder: GameStateHolder
) : ViewModel() {
    val gameState: StateFlow<Game?> = gameStateHolder.gameState
    val animations: StateFlow<List<Animation>> = gameStateHolder.animations

    fun makeMove(direction: Direction) = gameStateHolder.makeMove(direction)
    fun startNewGame() = gameStateHolder.startNewGame()

    override fun onCleared() {
        gameStateHolder.clear()
        super.onCleared()
    }
}