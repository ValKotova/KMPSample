package com.magni.game2048.feature.game

import com.magni.game2048.feature.game.CheckGameOverUseCase
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CheckGameOverUseCaseTest {
    private val useCase = com.magni.game2048.feature.game.CheckGameOverUseCase()

    @Test
    fun shouldReturnFalseWhenEmptyCellsExist() {
        // Given - grid with empty cells
        val grid = createGrid(4, listOf(
            2, 4, 2, 4,
            4, 2, 4, 2,
            2, 4, null, 4,
            4, 2, 4, 2
        ))

        // When
        val result = useCase(grid)

        // Then
        assertFalse(result)
    }

    @Test
    fun shouldReturnFalseWhenMergesArePossible() {
        // Given - full grid but with possible merges
        val grid = createGrid(4, listOf(
            2, 4, 2, 4,
            4, 2, 4, 2,
            2, 4, 2, 4,
            4, 2, 4, 2
        ))

        // When
        val result = useCase(grid)

        // Then
        assertFalse(result) // Not game over because many possible merges
    }

    @Test
    fun shouldReturnTrueWhenNoMovesPossible() {
        // Given - full grid with no possible merges
        val grid = createGrid(4, listOf(
            2, 4, 2, 4,
            4, 2, 4, 2,
            2, 4, 2, 4,
            4, 2, 4, 2
        ))

        // When
        val result = useCase(grid)

        // Then
        assertTrue(result)
    }

    @Test
    fun shouldReturnTrueForComplexNoMoveScenario() {
        // Given - a grid where no moves are possible
        val grid = createGrid(4, listOf(
            2, 4, 8, 16,
            4, 8, 16, 2,
            8, 16, 2, 4,
            16, 2, 4, 8
        ))

        // When
        val result = useCase(grid)

        // Then
        assertTrue(result)
    }

    @Test
    fun shouldReturnFalseWhenVerticalMergesArePossible() {
        // Given - grid with vertical merge possible
        val grid = createGrid(4, listOf(
            2, 4, 8, 16,
            2, 8, 16, 4,
            8, 16, 4, 8,
            16, 4, 8, 16
        ))

        // When
        val result = useCase(grid)

        // Then
        assertFalse(result) // First column has two 2's that can merge
    }

    @Test
    fun shouldReturnFalseWhenHorizontalMergesArePossible() {
        // Given - grid with horizontal merge possible
        val grid = createGrid(4, listOf(
            2, 2, 8, 16,
            4, 8, 16, 4,
            8, 16, 4, 8,
            16, 4, 8, 16
        ))

        // When
        val result = useCase(grid)

        // Then
        assertFalse(result) // First row has two 2's that can merge
    }
}
