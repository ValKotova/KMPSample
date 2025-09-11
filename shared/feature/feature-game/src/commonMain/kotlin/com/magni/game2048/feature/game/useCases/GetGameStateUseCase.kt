package com.magni.game2048.feature.game.useCases

import com.magni.game2048.feature.game.entity.Game
import com.magni.game2048.feature.game.repo.GameRepository

class GetGameStateUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(): Game? = gameRepository.loadGame()
}