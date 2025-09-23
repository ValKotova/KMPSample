package com.magni.game2048.feature.game

import com.magni.game2048.core.domain.entity.MoveResult
import com.magni.game2048.feature.game.StartNewGameUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class StartNewGameUseCaseTest {
    private val gameRepository = MockGameRepository()
    private val settingsRepository = MockSettingsRepository()
    private val useCase =
        com.magni.game2048.feature.game.StartNewGameUseCase(gameRepository, settingsRepository)

    @Test
    fun shouldCreateANewGameWithInitialState() {
        // When
        val game = runBlocking { useCase() }

        // Then
        assertNotNull(game)
        assertEquals(0, game.score)
        assertEquals(0, game.maxTile)
        assertFalse(game.isGameOver)

        // Should have exactly 2 tiles on the board
        val tileCount = game.grid.cells.flatten().count { it.value != null }
        assertEquals(2, tileCount)

        // All tiles should be either 2 or 4 (depending on difficulty)
        val tileValues = game.grid.cells.flatten().mapNotNull { it.value }
        assertTrue(tileValues.all { it == 2 || it == 4 })
    }

    @Test
    fun shouldClearMoveHistoryWhenStartingNewGame() {
        // Given - add some moves to history
        runBlocking {
            gameRepository.saveMove(MoveResult(createGrid(4, listOf(2, 2, 2, 2)), 4, emptyList()))

            // When
            useCase()

            // Then
            assertTrue(gameRepository.getMoveHistory().isEmpty())
        }
    }
}