package com.magni.game2048.core.database.mapper

import com.magni.game2048.core.database.entity.RealmGame
import com.magni.game2048.core.database.entity.RealmGameHistory
import com.magni.game2048.core.domain.entity.Game

class RealmMapper {
    private val realmGameMapper = RealmGameMapper()
    private val realmGameHistoryMapper = RealmGameHistoryMapper()

    fun toRealmGame(game: Game): RealmGame = realmGameMapper.toRealmGame(game)
    fun toGame(realmGame: RealmGame): Game = realmGameMapper.toGame(realmGame)
    fun toRealmGameHistory(game: Game, historyId: String): RealmGameHistory =
        realmGameHistoryMapper.toRealmGameHistory(game, historyId)
    fun toGame(realmGameHistory: RealmGameHistory): Game = realmGameHistoryMapper.toGame(realmGameHistory)
}