package com.magni.game2048.feature.game.useCases

import com.magni.game2048.core.domain.entity.Difficulty
import com.magni.game2048.core.domain.entity.Grid
import com.magni.game2048.core.domain.entity.Position

interface TileGenerator {
    fun generateTile(grid: Grid, difficulty: Difficulty): Pair<Position, Int>?
}

class RandomTileGenerator : TileGenerator {
    override fun generateTile(grid: Grid, difficulty: Difficulty): Pair<Position, Int>? {
        val emptyCells = grid.cells.flatMapIndexed { x, row ->
            row.mapIndexedNotNull { y, cell ->
                if (cell.value == null) Position(x, y) else null
            }
        }
        if (emptyCells.isEmpty()) return null

        val position = emptyCells.random()
        val value = when (difficulty) {
            Difficulty.EASY -> listOf(1, 2).random()
            Difficulty.MEDIUM -> listOf(1, 2, 4).random()
            Difficulty.HARD -> listOf(1, 2, 4, 8, 16).random()
        }
        return position to value
    }
}