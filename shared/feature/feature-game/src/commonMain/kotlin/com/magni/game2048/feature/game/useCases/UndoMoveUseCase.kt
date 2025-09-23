package com.magni.game2048.feature.game.useCases

import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.Grid
import com.magni.game2048.core.domain.repository.GameRepository

class UndoMoveUseCase constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(currentGame: Game): Game? {
        val moves = gameRepository.getMoveHistory()
        if (moves.size <= 1) return null

        val previousMove = moves[moves.size - 2]

        val subsequentMoves = moves.subList(moves.size - 1, moves.size)
        val scoreToSubtract = subsequentMoves.sumOf { it.scoreDelta }
        val previousScore = currentGame.score - scoreToSubtract

        // Create the previous game state
        val previousGame = currentGame.copy(
            grid = previousMove.newGrid,
            score = previousScore,
            maxTile = previousMove.newGrid.cells.flatten()
                .maxOfOrNull { it.value ?: 0 } ?: 0,
            isGameOver = CheckGameOverUseCase()(previousMove.newGrid)
        )

        // Save the updated game state
        gameRepository.saveGame(previousGame)

        // Remove moves after the undo point
        val updatedMoves = moves.subList(0, moves.size - 1)
        gameRepository.clearMoveHistory()
        updatedMoves.forEach { gameRepository.saveMove(it) }

        return previousGame
    }
}