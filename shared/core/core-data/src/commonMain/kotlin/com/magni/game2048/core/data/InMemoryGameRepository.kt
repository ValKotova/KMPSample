package com.magni.game2048.core.data

import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.repository.GameRepository

class InMemoryGameRepository : GameRepository {
    private var currentGame: Game? = null
    private val gameHistory = mutableListOf<Game>()

    override suspend fun saveGame(game: Game) {
        currentGame = game
        println("GAME_DEBUG: InMemoryGameRepository - Saved current game - score: ${game.score}, tiles: ${game.grid.cells.flatten().count { it.value != null }}")
    }

    override suspend fun loadGame(): Game? {
        return currentGame.also {
            println("GAME_DEBUG: InMemoryGameRepository - Loaded current game - ${it != null}")
        }
    }

    override suspend fun saveGameToHistory(game: Game) {
        gameHistory.add(game)
        println("GAME_DEBUG: InMemoryGameRepository - Saved to history - history size: ${gameHistory.size}, score: ${game.score}")
    }

    override suspend fun getPreviousGameState(): Game? {
        return if (gameHistory.isNotEmpty()) {
            val previousGame = gameHistory.last()
            println("GAME_DEBUG: InMemoryGameRepository - Returning previous state - score: ${previousGame.score}, tiles: ${previousGame.grid.cells.flatten().count { it.value != null }}")
            previousGame
        } else {
            println("GAME_DEBUG: InMemoryGameRepository - No previous state - history size: ${gameHistory.size}")
            null
        }
    }

    override suspend fun removeLastGameFromHistory() {
        if (gameHistory.isNotEmpty()) {
            gameHistory.removeAt(gameHistory.size - 1)
            println("GAME_DEBUG: InMemoryGameRepository - Removed from history - new size: ${gameHistory.size}")
        }
    }

    override suspend fun clearGameHistory() {
        val previousSize = gameHistory.size
        gameHistory.clear()
        println("GAME_DEBUG: InMemoryGameRepository - Cleared game history - removed $previousSize entries")
    }

    override suspend fun clearAllData() {
        val historySize = gameHistory.size
        gameHistory.clear()
        currentGame = null
        println("GAME_DEBUG: InMemoryGameRepository - Cleared ALL data - history: $historySize entries, current game: cleared")
    }

    override suspend fun getHistorySize(): Int {
        return gameHistory.size
    }

    override fun close() {
        // No cleanup needed
    }
}