package com.magni.game2048.core.database.mapper

import com.magni.game2048.core.database.entity.RealmRecord
import com.magni.game2048.core.domain.entity.GameRecord

class RealmRecordsMapper {

    fun toRealmRecord(record: GameRecord): RealmRecord {
        return RealmRecord().apply {
            id = record.id
            score = record.score
            maxTile = record.maxTile
            date = record.date
            difficulty = record.difficulty.name
            playerName = record.playerName
        }
    }

    fun toGameRecord(realmRecord: RealmRecord): GameRecord {
        return GameRecord(
            id = realmRecord.id,
            score = realmRecord.score,
            maxTile = realmRecord.maxTile,
            date = realmRecord.date,
            difficulty = enumValueOf(realmRecord.difficulty),
            playerName = realmRecord.playerName
        )
    }
}