package com.magni.game2048.feature.game.presentation

import com.magni.game2048.feature.game.entity.Animation
import com.magni.game2048.feature.game.entity.Direction
import com.magni.game2048.feature.game.entity.Game
import com.magni.game2048.feature.game.useCases.MakeMoveUseCase
import com.magni.game2048.feature.game.useCases.StartNewGameUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameStateHolder(
    private val makeMoveUseCase: MakeMoveUseCase,
    private val startNewGameUseCase: StartNewGameUseCase
) {
    private val _gameState = MutableStateFlow<Game?>(null)
    val gameState: StateFlow<Game?> = _gameState.asStateFlow()

    private val _animations = MutableStateFlow<List<Animation>>(emptyList())
    val animations: StateFlow<List<Animation>> = _animations.asStateFlow()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        startNewGame()
    }

    fun makeMove(direction: Direction) {
        coroutineScope.launch {
            val currentGame = _gameState.value ?: return@launch
            val moveResult = makeMoveUseCase(currentGame, direction)

            _gameState.value = currentGame.copy(
                grid = moveResult.newGrid,
                score = currentGame.score + moveResult.scoreDelta,
                maxTile = maxOf(currentGame.maxTile, moveResult.newGrid.cells.flatten()
                    .maxOfOrNull { it.value ?: 0 } ?: 0)
            )

            _animations.value = moveResult.animations
        }
    }

    fun startNewGame() {
        coroutineScope.launch {
            val game = startNewGameUseCase()
            _gameState.value = game
            _animations.value = emptyList()
        }
    }

    fun clear() {
        coroutineScope.cancel()
    }
}