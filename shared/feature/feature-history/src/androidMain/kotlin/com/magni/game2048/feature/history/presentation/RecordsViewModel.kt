package com.magni.game2048.feature.history.presentation

import androidx.lifecycle.ViewModel
import com.magni.game2048.core.domain.entity.GameRecord
import kotlinx.coroutines.flow.StateFlow

class RecordsViewModel(
    val recordsStateHolder: RecordsStateHolder
) : ViewModel() {
    val records: StateFlow<List<GameRecord>> = recordsStateHolder.records

    fun loadRecords() = recordsStateHolder.loadRecords()
    fun clearRecords() = recordsStateHolder.clearRecords()

    override fun onCleared() {
        recordsStateHolder.clear()
        super.onCleared()
    }
}