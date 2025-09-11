package com.magni.game2048.feature.game.data

import com.magni.game2048.feature.game.entity.Game
import com.magni.game2048.feature.game.entity.MoveResult
import com.magni.game2048.feature.game.repo.GameRepository

class GameRepositoryImpl : GameRepository {
    private var currentGame: Game? = null
    private val moveHistory = mutableListOf<MoveResult>()

    override suspend fun saveGame(game: Game) {
        currentGame = game
    }

    override suspend fun loadGame(): Game? {
        return currentGame
    }

    override suspend fun getMoveHistory(): List<MoveResult> {
        return moveHistory.toList()
    }

    override suspend fun saveMove(move: MoveResult) {
        moveHistory.add(move)
    }

    override suspend fun clearMoveHistory() {
        moveHistory.clear()
    }
}
