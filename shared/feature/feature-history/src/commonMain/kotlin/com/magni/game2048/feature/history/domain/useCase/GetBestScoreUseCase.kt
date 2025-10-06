package com.magni.game2048.feature.history.domain.useCase

import com.magni.game2048.core.domain.repository.RecordsRepository

class GetBestScoreUseCase(private val recordsRepository: RecordsRepository) {
    suspend operator fun invoke(): Int = recordsRepository.getBestScore()
}