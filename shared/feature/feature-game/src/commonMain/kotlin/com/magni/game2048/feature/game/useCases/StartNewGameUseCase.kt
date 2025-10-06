package com.magni.game2048.feature.game.useCases

import com.magni.game2048.core.database.entity.generateCellId
import com.magni.game2048.core.domain.entity.Cell
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.Grid
import com.magni.game2048.core.domain.entity.MoveResult
import com.magni.game2048.core.domain.entity.Position
import com.magni.game2048.core.domain.repository.SettingsRepository
import com.magni.game2048.core.domain.repository.GameRepository
import com.magni.game2048.feature.history.domain.useCase.SaveRecordUseCase
import kotlin.random.Random

class StartNewGameUseCase(
    private val gameRepository: GameRepository,
    private val settingsRepository: SettingsRepository,
    private val saveRecordUseCase: SaveRecordUseCase
) {
    suspend operator fun invoke(): Game {
        val settings = settingsRepository.loadSettings()
        val currentGame = gameRepository.loadGame()
        if (currentGame != null && currentGame.isGameOver) {
            saveRecordUseCase.invoke(currentGame)
        }
        val grid = createEmptyGrid(settings.gridSize)
        val gridWithTiles = addRandomTile(grid, 2)
        val game = Game(
            id = Random.nextLong().toString(),
            grid = gridWithTiles,
            score = 0,
            maxTile = 2,
            isGameOver = false,
            difficulty = settings.difficulty
        )

        // Clear ALL history including the current game state
        gameRepository.clearAllData()
        gameRepository.saveGame(game)

        println("GAME_DEBUG: StartNewGame - Created game with ${gridWithTiles.cells.flatten().count { it.value != null }} tiles")
        return game
    }
    private fun createEmptyGrid(size: Int): Grid {
        val gridId = Random.nextLong().toString()
        return Grid(size, List(size) { x ->
            List(size) { y ->
                Cell(Position(x, y), null, generateCellId(gridId, x, y))
            }
        })
    }

    private fun addRandomTile(grid: Grid, count: Int): Grid {
        var currentGrid = grid
        val gridId = Random.nextLong().toString()

        repeat(count) {
            val emptyCells = currentGrid.cells.flatMapIndexed { x, row ->
                row.mapIndexedNotNull { y, cell ->
                    if (cell.value == null) Position(x, y) else null
                }
            }

            if (emptyCells.isNotEmpty()) {
                val randomPosition = emptyCells.random()
                val updatedCells = currentGrid.cells.mapIndexed { x, row ->
                    row.mapIndexed { y, cell ->
                        if (x == randomPosition.x && y == randomPosition.y) {
                            cell.copy(value = 2, id = generateCellId(gridId, x, y))
                        } else {
                            cell
                        }
                    }
                }
                currentGrid = currentGrid.copy(cells = updatedCells)
            }
        }
        return currentGrid
    }
}