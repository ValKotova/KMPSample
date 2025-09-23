package com.magni.game2048.core.database.mapper

import com.magni.game2048.core.database.entity.RealmGrid
import com.magni.game2048.core.database.entity.RealmMoveResult
import com.magni.game2048.core.domain.entity.MoveResult
import io.realm.kotlin.ext.realmListOf

class RealmMoveMapper(private val realmGridMapper: RealmGridMapper = RealmGridMapper()) {
    fun toRealmMove(move: MoveResult): RealmMoveResult {
        return RealmMoveResult(
            scoreDelta = move.scoreDelta,
            grid = realmGridMapper.toRealmGrid(move.newGrid)
        )
    }

    fun toMove(realmMove: RealmMoveResult): MoveResult {
        return MoveResult(
            newGrid = realmGridMapper.toGrid(realmMove.grid ?: createEmptyRealmGrid()),
            scoreDelta = realmMove.scoreDelta,
            animations = emptyList()
        )
    }

    private fun createEmptyRealmGrid(): RealmGrid {
        return RealmGrid(4, realmListOf())
    }
}