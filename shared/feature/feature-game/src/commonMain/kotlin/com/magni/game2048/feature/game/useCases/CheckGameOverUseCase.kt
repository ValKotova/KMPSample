package com.magni.game2048.feature.game.useCases

import com.magni.game2048.feature.game.entity.Grid

class CheckGameOverUseCase {
    operator fun invoke(grid: Grid): Boolean {
        val size = grid.size

        // Check for empty cells
        for (x in 0 until size) {
            for (y in 0 until size) {
                if (grid.cells[x][y].value == null) {
                    return false
                }
            }
        }

        // Check for possible merges
        for (x in 0 until size) {
            for (y in 0 until size) {
                val current = grid.cells[x][y].value

                // Check right neighbor
                if (y < size - 1 && grid.cells[x][y + 1].value == current) {
                    return false
                }

                // Check bottom neighbor
                if (x < size - 1 && grid.cells[x + 1][y].value == current) {
                    return false
                }
            }
        }

        return true
    }
}