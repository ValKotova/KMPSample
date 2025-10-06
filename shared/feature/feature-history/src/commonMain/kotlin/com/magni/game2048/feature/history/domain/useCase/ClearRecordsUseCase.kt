package com.magni.game2048.feature.history.domain.useCase

import com.magni.game2048.core.domain.repository.RecordsRepository

class ClearRecordsUseCase(private val recordsRepository: RecordsRepository) {
    suspend operator fun invoke() = recordsRepository.clearRecords()
}