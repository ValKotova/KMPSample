package com.magni.game2048.feature.game

import com.magni.game2048.core.domain.entity.Difficulty
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.MoveResult
import com.magni.game2048.feature.game.UndoMoveUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class UndoMoveUseCaseTest {
    private val gameRepository = MockGameRepository()
    private val useCase = com.magni.game2048.feature.game.UndoMoveUseCase(gameRepository)

    @Test
    fun shouldUndoTheLastMoveCorrectly() {
        runBlocking {
            // Given - a game with some move history
            val initialGrid = createGrid(4, listOf(
                2, 2, null, null,
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            val afterMoveGrid = createGrid(4, listOf(
                4, null, null, 2,
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            val game = Game("test", afterMoveGrid, 4, 4, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)
            gameRepository.saveMove(MoveResult(afterMoveGrid, 4, emptyList()))

            // When
            val result = useCase(game)

            // Then - should return to initial state
            assertNotNull(result)

            // Check the grid after undo
            assertGridEquals(initialGrid, result!!.grid)
            assertEquals(0, result.score) // Score should be reset
        }
    }

    @Test
    fun shouldReturnNullWhenNoMovesToUndo() {
        runBlocking {
            // Given - a game with no move history
            val grid = createGrid(4, listOf(
                2, 2, null, null,
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            val game = Game("test", grid, 0, 2, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)

            // When
            val result = useCase(game)

            // Then
            assertNull(result)
        }
    }
}