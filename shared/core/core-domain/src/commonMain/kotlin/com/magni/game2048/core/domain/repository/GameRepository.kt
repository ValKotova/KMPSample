package com.magni.game2048.core.domain.repository

import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.MoveResult

interface GameRepository {
    suspend fun saveGame(game: Game)
    suspend fun loadGame(): Game?
    suspend fun getMoveHistory(): List<MoveResult>
    suspend fun saveMove(move: MoveResult)
    suspend fun clearMoveHistory()
    fun close()
}