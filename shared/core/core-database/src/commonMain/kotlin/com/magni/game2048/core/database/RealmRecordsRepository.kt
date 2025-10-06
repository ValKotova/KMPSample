package com.magni.game2048.core.database

import com.magni.game2048.core.database.entity.RealmRecord
import com.magni.game2048.core.database.mapper.RealmRecordsMapper
import com.magni.game2048.core.domain.entity.GameRecord
import com.magni.game2048.core.domain.repository.RecordsRepository
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RealmRecordsRepository(
    private val realmRecordsMapper: RealmRecordsMapper = RealmRecordsMapper()
) : RecordsRepository {

    private val _recordsFlow = MutableStateFlow<List<GameRecord>>(emptyList())

    override suspend fun getRecords(): List<GameRecord> {
        return RealmManager.withRealm { realm ->
            realm.query(RealmRecord::class)
                .sort("score", Sort.DESCENDING)
                .find()
                .map { realmRecordsMapper.toGameRecord(it) }
        }.also {
            _recordsFlow.emit(it)
        }
    }

    override suspend fun saveRecord(record: GameRecord) {
        RealmManager.writeRealm { mutableRealm ->
            val realmRecord = realmRecordsMapper.toRealmRecord(record)
            mutableRealm.copyToRealm(realmRecord)
        }
        getRecords()
    }

    override suspend fun getBestScore(): Int {
        return RealmManager.withRealm { realm ->
            realm.query(RealmRecord::class)
                .sort("score", Sort.DESCENDING)
                .first()
                .find()
                ?.let { realmRecordsMapper.toGameRecord(it).score } ?: 0
        }
    }

    override suspend fun clearRecords() {
        RealmManager.writeRealm { mutableRealm ->
            mutableRealm.delete(RealmRecord::class)
        }
        _recordsFlow.value = emptyList()
    }

    override fun getRecordsFlow(): StateFlow<List<GameRecord>> = _recordsFlow
}