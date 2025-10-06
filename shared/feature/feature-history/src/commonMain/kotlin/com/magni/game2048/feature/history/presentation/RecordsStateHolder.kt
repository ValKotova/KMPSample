package com.magni.game2048.feature.history.presentation

import com.magni.game2048.core.domain.entity.GameRecord
import com.magni.game2048.core.domain.repository.RecordsRepository
import com.magni.game2048.feature.history.domain.useCase.ClearRecordsUseCase
import com.magni.game2048.feature.history.domain.useCase.GetRecordsFlowUseCase
import com.magni.game2048.feature.history.domain.useCase.GetRecordsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecordsStateHolder(
    private val getRecordsUseCase: GetRecordsUseCase,
    private val clearRecordsUseCase: ClearRecordsUseCase,
    getRecordsFlowUseCase: GetRecordsFlowUseCase
) {
    val records: StateFlow<List<GameRecord>> = getRecordsFlowUseCase()

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        loadRecords()
    }

    fun loadRecords() {
        coroutineScope.launch {
            getRecordsUseCase()
        }
    }

    fun clearRecords() {
        coroutineScope.launch {
            clearRecordsUseCase()
        }
    }

    fun clear() {
        coroutineScope.cancel()
    }
}