package com.magni.game2048.core.data

import com.magni.game2048.core.domain.entity.GameRecord
import com.magni.game2048.core.domain.repository.RecordsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InMemoryRecordsRepository : RecordsRepository {
    private val records = mutableListOf<GameRecord>()
    private val _recordsFlow = MutableStateFlow<List<GameRecord>>(emptyList())

    override suspend fun getRecords(): List<GameRecord> {
        return records.sortedByDescending { it.score }
    }

    override suspend fun saveRecord(record: GameRecord) {
        records.add(record)
        // Keep only top 100 records
        if (records.size > 100) {
            records.sortByDescending { it.score }
            records.subList(100, records.size).clear()
        }
        _recordsFlow.value = records.sortedByDescending { it.score }
    }

    override suspend fun getBestScore(): Int {
        return records.maxByOrNull { it.score }?.score ?: 0
    }

    override suspend fun clearRecords() {
        records.clear()
        _recordsFlow.value = emptyList()
    }

    override fun getRecordsFlow(): StateFlow<List<GameRecord>> = _recordsFlow
}