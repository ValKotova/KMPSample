package com.magni.game2048.core.domain.repository

import com.magni.game2048.core.domain.entity.GameRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RecordsRepository {
    suspend fun getRecords(): List<GameRecord>
    suspend fun saveRecord(record: GameRecord)
    suspend fun getBestScore(): Int
    suspend fun clearRecords()

    fun getRecordsFlow(): StateFlow<List<GameRecord>>
}