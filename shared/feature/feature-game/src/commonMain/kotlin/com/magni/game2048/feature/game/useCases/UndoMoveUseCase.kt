package com.magni.game2048.feature.game.useCases

import com.magni.game2048.feature.game.entity.Game
import com.magni.game2048.feature.game.entity.Grid
import com.magni.game2048.feature.game.repo.GameRepository

class UndoMoveUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(currentGame: Game): Game? {
        val moves = gameRepository.getMoveHistory()
        if (moves.isEmpty()) return null

        // Get the previous move
        val previousMove = moves.last()

        // Calculate previous state
        val previousGrid = if (moves.size == 1) {
            // If only one move, revert to initial state
            val initialGrid = currentGame.grid.cells.map { row ->
                row.map { cell ->
                    cell.copy(value = null)
                }
            }
            Grid(currentGame.grid.size, initialGrid)
        } else {
            // Otherwise, use the grid from the previous move
            moves[moves.size - 2].newGrid
        }

        // Calculate previous score
        val previousScore = currentGame.score - previousMove.scoreDelta

        // Update game state
        val updatedGame = currentGame.copy(
            grid = previousGrid,
            score = previousScore,
            maxTile = previousGrid.cells.flatten().maxOfOrNull { it.value ?: 0 } ?: 0,
            isGameOver = CheckGameOverUseCase()(previousGrid)
        )

        // Save the updated game
        gameRepository.saveGame(updatedGame)

        // Remove the last move from history
        val updatedMoves = moves.dropLast(1)
        gameRepository.clearMoveHistory()
        updatedMoves.forEach { gameRepository.saveMove(it) }

        return updatedGame
    }
}