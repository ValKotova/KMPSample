package com.magni.game2048.core.data

import com.magni.game2048.core.database.RealmManager
import com.magni.game2048.core.database.RealmOperations
import com.magni.game2048.core.database.RealmOperationsImpl
import com.magni.game2048.core.database.entity.RealmGame
import com.magni.game2048.core.database.entity.RealmMoveResult
import com.magni.game2048.core.database.mapper.RealmMapper
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.MoveResult
import com.magni.game2048.core.domain.repository.GameRepository

class RealmGameRepository(
    private val realmOperations: RealmOperations = RealmOperationsImpl(),
    private val realmMapper: RealmMapper = RealmMapper()
) : GameRepository {

    override suspend fun saveGame(game: Game) {
        val realmGame = realmMapper.toRealmGame(game)
        realmOperations.save(realmGame)
    }

    override suspend fun loadGame(): Game? {
        val realmGames = realmOperations.query(RealmGame::class)
        return realmGames.firstOrNull()?.let {
            realmMapper.toGame(it)
        }
    }

    override suspend fun getMoveHistory(): List<MoveResult> {
        val realmMoves = realmOperations.query(RealmMoveResult::class)
        return realmMoves.map { realmMapper.toMove(it) }
    }

    override suspend fun saveMove(move: MoveResult) {
        val realmMove = realmMapper.toRealmMove(move)
        realmOperations.save(realmMove)
    }

    override suspend fun clearMoveHistory() {
        realmOperations.delete(RealmMoveResult::class)
    }

    override fun close() {
        RealmManager.closeRealm()
    }
}