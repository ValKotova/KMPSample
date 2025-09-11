package com.magni.game2048.feature.game.repo

import com.magni.game2048.feature.game.entity.Game
import com.magni.game2048.feature.game.entity.MoveResult

interface GameRepository {
    suspend fun saveGame(game: Game)
    suspend fun loadGame(): Game?
    suspend fun getMoveHistory(): List<MoveResult>
    suspend fun saveMove(move: MoveResult)
    suspend fun clearMoveHistory()
}