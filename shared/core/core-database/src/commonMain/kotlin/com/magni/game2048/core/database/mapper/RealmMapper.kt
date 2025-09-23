package com.magni.game2048.core.database.mapper

import com.magni.game2048.core.database.entity.RealmGame
import com.magni.game2048.core.database.entity.RealmMoveResult
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.MoveResult

class RealmMapper {
    private val realmGameMapper = RealmGameMapper()
    private val realmMoveMapper = RealmMoveMapper()

    fun toRealmGame(game: Game): RealmGame = realmGameMapper.toRealmGame(game)
    fun toGame(realmGame: RealmGame): Game = realmGameMapper.toGame(realmGame)
    fun toRealmMove(move: MoveResult): RealmMoveResult = realmMoveMapper.toRealmMove(move)
    fun toMove(realmMove: RealmMoveResult): MoveResult = realmMoveMapper.toMove(realmMove)
}