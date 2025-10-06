package com.magni.game2048.core.database

import com.magni.game2048.core.database.entity.RealmGame
import com.magni.game2048.core.database.entity.RealmGameHistory
import com.magni.game2048.core.database.mapper.RealmMapper
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.repository.GameRepository
import io.realm.kotlin.query.Sort
import kotlin.random.Random

class RealmGameRepository(
    private val realmMapper: RealmMapper = RealmMapper()
) : GameRepository {

    override suspend fun saveGame(game: Game) {
        RealmManager.writeRealm { mutableRealm ->
            mutableRealm.delete(RealmGame::class)

            val realmGame = realmMapper.toRealmGame(game)
            mutableRealm.copyToRealm(realmGame)
        }
    }

    override suspend fun loadGame(): Game? {
        return RealmManager.withRealm { realm ->
            val realmGames = realm.query(RealmGame::class).find()
            realmGames.lastOrNull()?.let {
                realmMapper.toGame(it)
            }
        }
    }

    override suspend fun saveGameToHistory(game: Game) {
        RealmManager.writeRealm { mutableRealm ->
            val historyId = Random.nextLong().toString()
            val realmGameHistory = realmMapper.toRealmGameHistory(game, historyId)
            mutableRealm.copyToRealm(realmGameHistory)
        }
    }

    override suspend fun getPreviousGameState(): Game? {
        return RealmManager.withRealm { realm ->
            val query = realm.query(RealmGameHistory::class)
            val sortedResults = query.sort("createdAt", Sort.DESCENDING).find()

            if (sortedResults.isNotEmpty()) {
                val lastHistory = sortedResults.first()
                val previousGame = realmMapper.toGame(lastHistory)
                previousGame
            } else {
                null
            }
        }
    }

    override suspend fun removeLastGameFromHistory() {
        RealmManager.writeRealm { mutableRealm ->
            val query = mutableRealm.query(RealmGameHistory::class)
            val sortedResults = query.sort("createdAt", Sort.DESCENDING).find()

            if (sortedResults.isNotEmpty()) {
                val lastHistory = sortedResults.first()
                mutableRealm.delete(lastHistory)
            }
        }
    }

    override suspend fun clearGameHistory() {
        RealmManager.writeRealm { mutableRealm ->
            val beforeCount = mutableRealm.query(RealmGameHistory::class).find().size
            mutableRealm.delete(RealmGameHistory::class)
            val afterCount = mutableRealm.query(RealmGameHistory::class).find().size

            println("GAME_DEBUG: RealmGameRepository - Cleared game history - before: $beforeCount, after: $afterCount")
        }
    }

    override suspend fun clearAllData() {
        RealmManager.writeRealm { mutableRealm ->
            val gamesBefore = mutableRealm.query(RealmGame::class).find().size
            val historyBefore = mutableRealm.query(RealmGameHistory::class).find().size

            mutableRealm.delete(RealmGame::class)
            mutableRealm.delete(RealmGameHistory::class)

            val gamesAfter = mutableRealm.query(RealmGame::class).find().size
            val historyAfter = mutableRealm.query(RealmGameHistory::class).find().size

            println("GAME_DEBUG: RealmGameRepository - Cleared ALL data")
            println("GAME_DEBUG: RealmGameRepository - Games: $gamesBefore -> $gamesAfter")
            println("GAME_DEBUG: RealmGameRepository - History: $historyBefore -> $historyAfter")
        }
    }

    override suspend fun getHistorySize(): Int {
        return RealmManager.withRealm { realm ->
            realm.query(RealmGameHistory::class).find().size
        }
    }

    override fun close() {
        RealmManager.closeRealm()
    }
}