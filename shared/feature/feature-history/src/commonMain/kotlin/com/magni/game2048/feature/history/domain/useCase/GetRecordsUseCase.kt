package com.magni.game2048.feature.history.domain.useCase

import com.magni.game2048.core.domain.entity.GameRecord
import com.magni.game2048.core.domain.repository.RecordsRepository

class GetRecordsUseCase(private val recordsRepository: RecordsRepository) {
    suspend operator fun invoke(): List<GameRecord> = recordsRepository.getRecords()
}
