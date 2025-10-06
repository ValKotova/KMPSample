package com.magni.game2048.feature.history.domain.useCase

import com.magni.game2048.core.domain.entity.GameRecord
import com.magni.game2048.core.domain.repository.RecordsRepository
import kotlinx.coroutines.flow.StateFlow

class GetRecordsFlowUseCase(private val recordsRepository: RecordsRepository) {
    operator fun invoke(): StateFlow<List<GameRecord>> = recordsRepository.getRecordsFlow()
}