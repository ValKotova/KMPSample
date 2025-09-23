package com.magni.game2048.core.data

import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.MoveResult
import com.magni.game2048.core.domain.repository.GameRepository

class InMemoryGameRepository : GameRepository {
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

    override fun close() {
        // No cleanup needed for in-memory storage
    }
}
