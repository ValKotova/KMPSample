package com.magni.game2048.core.data

import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.repository.GameRepository

class InMemoryGameRepository : GameRepository {
    private var currentGame: Game? = null
    private val gameHistory = mutableListOf<Game>()

    override suspend fun saveGame(game: Game) {
        currentGame = game
    }

    override suspend fun loadGame(): Game? {
        return currentGame
    }

    override suspend fun saveGameToHistory(game: Game) {
        gameHistory.add(game)
    }

    override suspend fun getPreviousGameState(): Game? {
        return if (gameHistory.isNotEmpty()) {
            val previousGame = gameHistory.last()
            previousGame
        } else {
            null
        }
    }

    override suspend fun removeLastGameFromHistory() {
        if (gameHistory.isNotEmpty()) {
            gameHistory.removeAt(gameHistory.size - 1)
        }
    }

    override suspend fun clearGameHistory() {
        gameHistory.clear()
    }

    override suspend fun clearAllData() {
        gameHistory.clear()
        currentGame = null
    }

    override suspend fun getHistorySize(): Int {
        return gameHistory.size
    }

    override fun close() {
        // No cleanup needed
    }
}