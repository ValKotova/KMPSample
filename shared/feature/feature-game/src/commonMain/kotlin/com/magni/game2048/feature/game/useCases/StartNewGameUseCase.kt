package com.magni.game2048.feature.game.useCases

import com.magni.game2048.core.domain.entity.Cell
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.Grid
import com.magni.game2048.core.domain.entity.MoveResult
import com.magni.game2048.core.domain.entity.Position
import com.magni.game2048.feature.settings.repo.SettingsRepository
import com.magni.game2048.core.domain.repository.GameRepository
import kotlin.random.Random

class StartNewGameUseCase(
    private val gameRepository: GameRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Game {
        val settings = settingsRepository.loadSettings()
        val grid = createEmptyGrid(settings.gridSize)
        val gridWithTiles = addRandomTile(grid, 2)
        val game = Game(
            id = Random.nextLong().toString(),
            grid = grid,
            score = 0,
            maxTile = 2,
            isGameOver = false,
            difficulty = settings.difficulty
        )

        gameRepository.saveGame(game)
        gameRepository.clearMoveHistory()
        val initialMove = MoveResult(gridWithTiles, 0, emptyList())
        gameRepository.saveMove(initialMove)

        return game
    }

    private fun createEmptyGrid(size: Int): Grid {
        return Grid(size, List(size) { x ->
            List(size) { y ->
                Cell(Position(x, y), null, Random.nextLong())
            }
        })
    }

    private fun addRandomTile(grid: Grid, count: Int): Grid {
        var currentGrid = grid
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
                            cell.copy(value = 2)
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