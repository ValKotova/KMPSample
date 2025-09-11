package com.magni.game2048.feature.game

import com.magni.game2048.feature.game.entity.Game
import com.magni.game2048.feature.game.entity.MoveResult
import com.magni.game2048.feature.game.repo.GameRepository

class MockGameRepository : GameRepository {
    private var currentGame: Game? = null
    private val moveHistory = mutableListOf<MoveResult>()

    override suspend fun saveGame(game: Game) {
        currentGame = game
    }

    override suspend fun loadGame(): Game? = currentGame

    override suspend fun getMoveHistory(): List<MoveResult> = moveHistory.toList()

    override suspend fun saveMove(move: MoveResult) {
        moveHistory.add(move)
    }

    override suspend fun clearMoveHistory() {
        moveHistory.clear()
    }
}