package com.magni.game2048.core.domain.repository

import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.MoveResult

interface GameRepository {
    suspend fun saveGame(game: Game)
    suspend fun loadGame(): Game?
    suspend fun saveGameToHistory(game: Game)
    suspend fun getPreviousGameState(): Game?
    suspend fun removeLastGameFromHistory()
    suspend fun clearGameHistory()
    suspend fun clearAllData()
    suspend fun getHistorySize(): Int
    fun close()
}