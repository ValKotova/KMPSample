package com.magni.game2048.feature.game.useCases

import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.Grid
import com.magni.game2048.core.domain.repository.GameRepository

class UndoMoveUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(currentGame: Game): Game? {

        // Get the last saved state from history (this is the state BEFORE the new tile was added)
        val previousGame = gameRepository.getPreviousGameState()
        if (previousGame == null) {
            return null
        }

        // Save the previous state as current
        gameRepository.saveGame(previousGame)

        // Remove the state we just used from history
        gameRepository.removeLastGameFromHistory()

        return previousGame
    }
}