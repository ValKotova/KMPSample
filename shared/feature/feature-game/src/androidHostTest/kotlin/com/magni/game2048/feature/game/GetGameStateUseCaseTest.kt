package com.magni.game2048.feature.game

import com.magni.game2048.feature.settings.entity.Difficulty
import com.magni.game2048.feature.game.entity.Game
import com.magni.game2048.feature.game.GetGameStateUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class GetGameStateUseCaseTest {
    private val gameRepository = MockGameRepository()
    private val useCase = com.magni.game2048.feature.game.GetGameStateUseCase(gameRepository)

    @Test
    fun shouldReturnCurrentGameState() {
        runBlocking {
            // Given
            val grid = createGrid(4, listOf(
                2, 2, null, null,
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            val game = Game("test", grid, 0, 2, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)

            // When
            val result = useCase()

            // Then
            assertNotNull(result)
            assertEquals(game.id, result!!.id)
            assertEquals(game.score, result.score)
        }
    }
}