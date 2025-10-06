package com.magni.game2048.feature.game.presentation

import com.magni.game2048.core.domain.entity.Animation
import com.magni.game2048.core.domain.entity.AppSettings
import com.magni.game2048.core.domain.entity.Direction
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.useCase.GetSettingsFlowUseCase
import com.magni.game2048.feature.game.useCases.CheckGameOverUseCase
import com.magni.game2048.feature.game.useCases.GetGameStateUseCase
import com.magni.game2048.feature.game.useCases.MakeMoveUseCase
import com.magni.game2048.feature.game.useCases.StartNewGameUseCase
import com.magni.game2048.feature.game.useCases.UndoMoveUseCase
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
    private val startNewGameUseCase: StartNewGameUseCase,
    private val undoMoveUseCase: UndoMoveUseCase,
    private val getGameStateUseCase: GetGameStateUseCase,
    private val getSettingsFlowUseCase: GetSettingsFlowUseCase
) {
    private val _gameState = MutableStateFlow<Game?>(null)
    val gameState: StateFlow<Game?> = _gameState.asStateFlow()

    private val _animations = MutableStateFlow<List<Animation>>(emptyList())
    val animations: StateFlow<List<Animation>> = _animations.asStateFlow()

    val currentSettings: StateFlow<AppSettings> = getSettingsFlowUseCase()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        loadGame()
    }

    fun makeMove(direction: Direction) {
        coroutineScope.launch {
            val currentGame = _gameState.value ?: return@launch

            val moveResult = makeMoveUseCase(currentGame, direction)

            val newGameState = currentGame.copy(
                grid = moveResult.newGrid,
                score = currentGame.score + moveResult.scoreDelta,
                maxTile = maxOf(currentGame.maxTile, moveResult.newGrid.cells.flatten()
                    .maxOfOrNull { it.value ?: 0 } ?: 0),
                isGameOver = CheckGameOverUseCase()(moveResult.newGrid)
            )

            _gameState.value = newGameState
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

    fun undoMove() {
        coroutineScope.launch {
            val currentGame = _gameState.value ?: return@launch

            val previousGame = undoMoveUseCase(currentGame)
            if (previousGame != null) {
                _gameState.value = previousGame
                _animations.value = emptyList()
            }
        }
    }

    private fun loadGame() {
        coroutineScope.launch {
            val game = getGameStateUseCase()
            _gameState.value = game
            if (game == null) {
                startNewGame()
            }
        }
    }

    fun clear() {
        coroutineScope.cancel()
    }

    fun clearAnimations() {
        _animations.value = emptyList()
    }
}