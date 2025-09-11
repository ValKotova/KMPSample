package com.magni.game2048.feature.game

import com.magni.game2048.feature.game.entity.Animation
import com.magni.game2048.feature.settings.entity.Difficulty
import com.magni.game2048.feature.game.entity.Direction
import com.magni.game2048.feature.game.entity.Game
import com.magni.game2048.feature.game.entity.Position
import com.magni.game2048.feature.game.MakeMoveUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test


class MakeMoveUseCaseTest {
    private val gameRepository = MockGameRepository()

    @Test
    fun shouldHandleLeftMoveCorrectly() {
        runBlocking {
            // Given - a row with [2, 2, null, 4]
            val initialGrid = createGrid(4, listOf(
                2, 2, null, 4,
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            val game = Game("test", initialGrid, 0, 4, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)

            // Use test generator that doesn't add new tiles
            val testTileGenerator = TestTileGenerator()
            val useCase =
                com.magni.game2048.feature.game.MakeMoveUseCase(gameRepository, testTileGenerator)

            // When
            val result = useCase(game, Direction.LEFT)

            // Then - should become [4, 4, null, null] without new tile
            assertEquals("Score delta should be 4", 4, result.scoreDelta)

            val expectedGrid = createGrid(4, listOf(
                4, 4, null, null,
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            assertGridEquals(expectedGrid, result.newGrid)

            // Check animations
            assertEquals("Should have 2 animations", 2, result.animations.size)

            // Verify merge animation
            val mergeAnimations = result.animations.filterIsInstance<Animation.Merge>()
            assertEquals("Should have 1 merge animation", 1, mergeAnimations.size)
            assertEquals("Merge should create value 4", 4, mergeAnimations[0].newValue)

            // Verify move animation
            val moveAnimations = result.animations.filterIsInstance<Animation.Move>()
            assertEquals("Should have 1 move animation", 1, moveAnimations.size)
            assertEquals("Move should be value 4", 4, moveAnimations[0].value)
        }
    }

    @Test
    fun shouldHandleRightMoveCorrectly() {
        runBlocking {
            // Given - a row with [2, 2, null, 4]
            val initialGrid = createGrid(4, listOf(
                2, 2, null, 4,
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            val game = Game("test", initialGrid, 0, 4, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)

            // Use test generator that doesn't add new tiles
            val testTileGenerator = TestTileGenerator()
            val useCase =
                com.magni.game2048.feature.game.MakeMoveUseCase(gameRepository, testTileGenerator)

            // When
            val result = useCase(game, Direction.RIGHT)

            // Print animations for debugging
            println("Right move animations:")
            printAnimations(result.animations)

            // Then - should become [null, null, 4, 4] without new tile
            assertEquals("Score delta should be 4", 4, result.scoreDelta)

            val expectedGrid = createGrid(4, listOf(
                null, null, 4, 4,
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            assertGridEquals(expectedGrid, result.newGrid)

            // Check animations
            assertEquals("Should have 1 animation", 1, result.animations.size)

            // Verify animations
            val mergeAnimations = result.animations.filterIsInstance<Animation.Merge>()
            assertEquals("Should have 1 merge animation", 1, mergeAnimations.size)
        }
    }

    @Test
    fun shouldHandleUpMoveCorrectly() {
        runBlocking {
            // Given - a column with [2, 2, null, 4]
            val initialGrid = createGrid(4, listOf(
                2, null, null, null,
                2, null, null, null,
                null, null, null, null,
                4, null, null, null
            ))

            val game = Game("test", initialGrid, 0, 4, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)

            // Use test generator that doesn't add new tiles
            val testTileGenerator = TestTileGenerator()
            val useCase =
                com.magni.game2048.feature.game.MakeMoveUseCase(gameRepository, testTileGenerator)

            // When
            val result = useCase(game, Direction.UP)

            // Then - should become [4, 4, null, null] in first column without new tile
            assertEquals("Score delta should be 4", 4, result.scoreDelta)

            val expectedGrid = createGrid(4, listOf(
                4, null, null, null,
                4, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            assertGridEquals(expectedGrid, result.newGrid)

            // Check animations
            assertEquals("Should have 2 animations", 2, result.animations.size)

            // Verify merge animation
            val mergeAnimations = result.animations.filterIsInstance<Animation.Merge>()
            assertEquals("Should have 1 merge animation", 1, mergeAnimations.size)
            assertEquals("Merge should create value 4", 4, mergeAnimations[0].newValue)

            // Verify move animation
            val moveAnimations = result.animations.filterIsInstance<Animation.Move>()
            assertEquals("Should have 1 move animation", 1, moveAnimations.size)
            assertEquals("Move should be value 4", 4, moveAnimations[0].value)
        }
    }

    @Test
    fun shouldHandleDownMoveCorrectly() {
        runBlocking {
            // Given - a column with [2, 2, null, 4]
            val initialGrid = createGrid(4, listOf(
                2, null, null, null,
                2, null, null, null,
                null, null, null, null,
                4, null, null, null
            ))

            val game = Game("test", initialGrid, 0, 4, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)

            // Use test generator that doesn't add new tiles
            val testTileGenerator = TestTileGenerator()
            val useCase =
                com.magni.game2048.feature.game.MakeMoveUseCase(gameRepository, testTileGenerator)

            // When
            val result = useCase(game, Direction.DOWN)

            // Print animations for debugging
            println("Down move animations:")
            printAnimations(result.animations)

            // Then - should become [null, null, 4, 4] in first column without new tile
            assertEquals("Score delta should be 4", 4, result.scoreDelta)

            val expectedGrid = createGrid(4, listOf(
                null, null, null, null,
                null, null, null, null,
                4, null, null, null,
                4, null, null, null
            ))

            assertGridEquals(expectedGrid, result.newGrid)

            // Check animations
            assertEquals("Should have 1 animation", 1, result.animations.size)

            // Verify animations
            val mergeAnimations = result.animations.filterIsInstance<Animation.Merge>()
            assertEquals("Should have 1 merge animation", 1, mergeAnimations.size)
        }
    }

    @Test
    fun shouldHandleMultipleNumbersMoving() {
        runBlocking {
            // Given - a row with [2, null, 4, 8]
            val initialGrid = createGrid(4, listOf(
                2, null, 4, 8,
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            val game = Game("test", initialGrid, 0, 8, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)

            // Use test generator that doesn't add new tiles
            val testTileGenerator = TestTileGenerator()
            val useCase =
                com.magni.game2048.feature.game.MakeMoveUseCase(gameRepository, testTileGenerator)

            // When
            val result = useCase(game, Direction.LEFT)

            // Then - should become [2, 4, 8, null] without new tile
            assertEquals("Score delta should be 0", 0, result.scoreDelta)

            val expectedGrid = createGrid(4, listOf(
                2, 4, 8, null,
                null, null, null, null,
                null, null, null, null,
                null, null, null, null
            ))

            assertGridEquals(expectedGrid, result.newGrid)

            // Check animations - should have 2 move animations
            assertEquals("Should have 2 move animations", 2, result.animations.size)

            // Verify move animations
            val moveAnimations = result.animations.filterIsInstance<Animation.Move>()
            assertEquals("Should have 2 move animations", 2, moveAnimations.size)

            // 4 moves from (0,2) to (0,1)
            val move4 = moveAnimations.find { it.value == 4 }
            assertNotNull("Should have move animation for 4", move4)

            // 8 moves from (0,3) to (0,2)
            val move8 = moveAnimations.find { it.value == 8 }
            assertNotNull("Should have move animation for 8", move8)

            // Print animations for verification
            println("Multiple moves animations:")
            printAnimations(result.animations)
        }
    }

    @Test
    fun shouldHandleMultipleMergesInOneRowCorrectly() {
        runBlocking {
            // Given - a row with [2, 2, 2, 2]
            val initialGrid = createGrid(
                4, listOf(
                    2, 2, 2, 2,
                    null, null, null, null,
                    null, null, null, null,
                    null, null, null, null
                )
            )

            val game = Game("test", initialGrid, 0, 2, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)

            // Use test generator that doesn't add new tiles
            val testTileGenerator = TestTileGenerator()
            val useCase =
                com.magni.game2048.feature.game.MakeMoveUseCase(gameRepository, testTileGenerator)

            // When
            val result = useCase(game, Direction.LEFT)

            // Then - should become [4, 4, null, null] without new tile
            assertEquals("Score delta should be 8 for two merges", 8, result.scoreDelta)

            val expectedGrid = createGrid(
                4, listOf(
                    4, 4, null, null,
                    null, null, null, null,
                    null, null, null, null,
                    null, null, null, null
                )
            )

            assertGridEquals(
                expectedGrid,
                result.newGrid
            )

            // Check animations - should have 2 merge animations
            val mergeAnimations = result.animations.filterIsInstance<Animation.Merge>()
            assertEquals("Should have 2 merge animations", 2, mergeAnimations.size)

            // First merge (first two 2s)
            assertEquals("First merge should create value 4", 4, mergeAnimations[0].newValue)
            assertPositionsEqual(
                "First merge should involve positions (0,0) and (0,1)",
                listOf(Position(0, 0), Position(0, 1)), mergeAnimations[0].sources
            )

            // Second merge (next two 2s)
            assertEquals("Second merge should create value 4", 4, mergeAnimations[1].newValue)
            assertPositionsEqual(
                "Second merge should involve positions (0,2) and (0,3)",
                listOf(Position(0, 2), Position(0, 3)), mergeAnimations[1].sources
            )
        }
    }

    @Test
    fun shouldNotMergeAlreadyMergedTilesInSameMove() {
        runBlocking {
            // Given - a row with [2, 2, 4, 4]
            val initialGrid = createGrid(
                4, listOf(
                    2, 2, 4, 4,
                    null, null, null, null,
                    null, null, null, null,
                    null, null, null, null
                )
            )

            val game = Game("test", initialGrid, 0, 4, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)

            // Use test generator that doesn't add new tiles
            val testTileGenerator = TestTileGenerator()
            val useCase =
                com.magni.game2048.feature.game.MakeMoveUseCase(gameRepository, testTileGenerator)

            // When
            val result = useCase(game, Direction.LEFT)

            // Then - should become [4, 8, null, null] without new tile
            assertEquals("Score delta should be 12 for both merges", 12, result.scoreDelta)

            val expectedGrid = createGrid(
                4, listOf(
                    4, 8, null, null,
                    null, null, null, null,
                    null, null, null, null,
                    null, null, null, null
                )
            )

            assertGridEquals(
                expectedGrid,
                result.newGrid
            )

            // Check animations - should have 2 merge animations
            val mergeAnimations = result.animations.filterIsInstance<Animation.Merge>()
            assertEquals("Should have 2 merge animations", 2, mergeAnimations.size)

            // First merge (2+2=4)
            assertEquals("First merge should create value 4", 4, mergeAnimations[0].newValue)
            assertPositionsEqual(
                "First merge should involve positions (0,0) and (0,1)",
                listOf(Position(0, 0), Position(0, 1)), mergeAnimations[0].sources
            )

            // Second merge (4+4=8)
            assertEquals("Second merge should create value 8", 8, mergeAnimations[1].newValue)
            assertPositionsEqual(
                "Second merge should involve positions (0,2) and (0,3)",
                listOf(Position(0, 2), Position(0, 3)), mergeAnimations[1].sources
            )
        }
    }

    @Test
    fun shouldAddNewTileOnSuccessfulMove() {
        runBlocking {
            // Given - a row with [2, 2, null, 4]
            val initialGrid = createGrid(
                4, listOf(
                    2, 2, null, 4,
                    null, null, null, null,
                    null, null, null, null,
                    null, null, null, null
                )
            )

            val game = Game("test", initialGrid, 0, 4, false, Difficulty.MEDIUM)
            gameRepository.saveGame(game)

            // Use test generator that adds a specific tile
            val testTileGenerator = TestTileGenerator(Position(0, 2) to 2)
            val useCase =
                com.magni.game2048.feature.game.MakeMoveUseCase(gameRepository, testTileGenerator)

            // When
            val result = useCase(game, Direction.LEFT)

            // Then - should become [4, 4, 2, null] with the new tile
            assertEquals("Score delta should be 4", 4, result.scoreDelta)

            // Check that one of the animations is an Appear animation
            val appearAnimations = result.animations.filterIsInstance<Animation.Appear>()
            assertEquals("Should have one appear animation", 1, appearAnimations.size)
            assertEquals(
                "Appear animation should be at position (0,2)",
                Position(0, 2), appearAnimations[0].at
            )
            assertEquals("Appear animation should have value 2", 2, appearAnimations[0].value)

            // Should also have merge and move animations
            val mergeAnimations = result.animations.filterIsInstance<Animation.Merge>()
            assertEquals("Should have 1 merge animation", 1, mergeAnimations.size)

            val moveAnimations = result.animations.filterIsInstance<Animation.Move>()
            assertEquals("Should have 1 move animation", 1, moveAnimations.size)

            val expectedGrid = createGrid(
                4, listOf(
                    4, 4, 2, null,
                    null, null, null, null,
                    null, null, null, null,
                    null, null, null, null
                )
            )

            assertGridEquals(expectedGrid, result.newGrid)
        }
    }
}