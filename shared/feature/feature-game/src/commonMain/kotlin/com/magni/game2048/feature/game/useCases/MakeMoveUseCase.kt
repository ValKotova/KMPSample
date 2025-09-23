package com.magni.game2048.feature.game.useCases

import com.magni.game2048.feature.game.AnimationLogger
import com.magni.game2048.core.domain.entity.Animation
import com.magni.game2048.core.domain.entity.Cell
import com.magni.game2048.core.domain.entity.Direction
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.Grid
import com.magni.game2048.core.domain.entity.MoveResult
import com.magni.game2048.core.domain.entity.Position
import com.magni.game2048.core.domain.repository.GameRepository
import kotlin.random.Random

class MakeMoveUseCase(
    private val gameRepository: GameRepository,
    private val tileGenerator: TileGenerator
) {
    suspend operator fun invoke(game: Game, direction: Direction): MoveResult {
        AnimationLogger.log(message = "Processing move in direction: $direction")
        AnimationLogger.log(message = "Original grid: ${formatGridForLog(game.grid)}")

        // Rotate grid to make the direction always "left" for processing
        val (rotatedGrid, rotationCount) = rotateGridForDirection(game.grid, direction)
        AnimationLogger.log(message = "Rotated grid (rotationCount: $rotationCount): ${formatGridForLog(rotatedGrid)}")

        // Process the move as if it were a left move
        val (processedGrid, scoreDelta, animations) = processLeftMove(rotatedGrid)
        AnimationLogger.log(message = "Processed grid after left move: ${formatGridForLog(processedGrid)}")
        AnimationLogger.log(message = "Animations from left move: $animations")

        // Rotate the grid and animations back to the original orientation
        val (finalGrid, adjustedAnimations) = rotateBack(processedGrid, animations, direction, rotationCount)
        AnimationLogger.log(message = "Final grid after rotation: ${formatGridForLog(finalGrid)}")
        AnimationLogger.log(message = "Adjusted animations: $adjustedAnimations")

        // Add new tile if move was successful using the tileGenerator
        val gridWithNewTile = if (adjustedAnimations.isNotEmpty()) {
            tileGenerator.generateTile(finalGrid, game.difficulty)?.let { (position, value) ->
                val newCell = Cell(position, value, Random.nextLong())
                val updatedCells = finalGrid.cells.mapIndexed { x, row ->
                    row.mapIndexed { y, cell ->
                        if (x == position.x && y == position.y) newCell else cell
                    }
                }
                adjustedAnimations.add(Animation.Appear(position, value, newCell))
                Grid(finalGrid.size, updatedCells)
            } ?: finalGrid
        } else {
            finalGrid
        }

        val moveResult = MoveResult(gridWithNewTile, scoreDelta, adjustedAnimations)
        AnimationLogger.log(message = "Final move result: ${moveResult.animations}")

        return moveResult
    }

    private fun formatGridForLog(grid: Grid): String {
        return grid.cells.joinToString("\n") { row ->
            row.joinToString(" ") { cell -> cell.value?.toString() ?: "null" }
        }
    }

    private fun rotateGridForDirection(grid: Grid, direction: Direction): Pair<Grid, Int> {
        return when (direction) {
            Direction.LEFT -> Pair(grid, 0)
            Direction.RIGHT -> Pair(rotateGrid(grid, 2), 2) // 180 degrees
            Direction.UP -> Pair(rotateGrid(grid, 3), 3) // 270 degrees clockwise
            Direction.DOWN -> Pair(rotateGrid(grid, 1), 1) // 90 degrees clockwise
        }
    }

    private fun rotateBack(grid: Grid, animations: MutableList<Animation>, direction: Direction, rotationCount: Int): Pair<Grid, MutableList<Animation>> {
        val rotationsNeeded = when (direction) {
            Direction.LEFT -> 0
            Direction.RIGHT -> 2 // Rotate back 180 degrees
            Direction.UP -> 1 // Rotate back 90 degrees clockwise (from 270)
            Direction.DOWN -> 3 // Rotate back 270 degrees clockwise (from 90)
        }

        val rotatedGrid = rotateGrid(grid, rotationsNeeded)

        // Use inverse rotation for animations (4 - rotationCount) % 4
        val inverseRotation = (4 - rotationCount) % 4
        val adjustedAnimations = animations.map { adjustAnimationForRotation(it, inverseRotation, grid.size) }.toMutableList()

        AnimationLogger.log(message = "Inverse rotation for animations: $inverseRotation")
        AnimationLogger.log(message = "Rotated grid size: ${grid.size}")

        return Pair(rotatedGrid, adjustedAnimations)
    }

    private fun rotateGrid(grid: Grid, rotations: Int): Grid {
        var result = grid
        for (i in 0 until rotations) {
            result = rotate90Clockwise(result)
        }
        return result
    }

    private fun rotate90Clockwise(grid: Grid): Grid {
        val size = grid.size
        val newCells = MutableList(size) { x ->
            MutableList(size) { y ->
                val originalCell = grid.cells[size - 1 - y][x]
                originalCell.copy(position = Position(x, y))
            }
        }
        return Grid(size, newCells)
    }

    private fun adjustAnimationForRotation(animation: Animation, rotationCount: Int, size: Int): Animation {
        if (rotationCount == 0) return animation

        AnimationLogger.log(message = "Adjusting animation: $animation with rotationCount: $rotationCount")

        return when (animation) {
            is Animation.Move -> {
                var from = animation.from
                var to = animation.to
                for (i in 0 until rotationCount) {
                    from = rotate90Clockwise(from, size)
                    to = rotate90Clockwise(to, size)
                }
                animation.copy(from = from, to = to)
            }
            is Animation.Merge -> {
                var sources = animation.sources
                var destination = animation.destination
                for (i in 0 until rotationCount) {
                    sources = sources.map { rotate90Clockwise(it, size) }
                    destination = rotate90Clockwise(destination, size)
                }
                animation.copy(sources = sources, destination = destination)
            }
            is Animation.Appear -> {
                var at = animation.at
                for (i in 0 until rotationCount) {
                    at = rotate90Clockwise(at, size)
                }
                animation.copy(at = at)
            }
        }
    }

    private fun rotate90Clockwise(position: Position, size: Int): Position {
        // Rotate 90 degrees clockwise: (x, y) -> (y, size-1-x)
        val newX = position.y
        val newY = size - 1 - position.x
        AnimationLogger.log(message = "Rotating position: $position -> ($newX, $newY)")
        return Position(newX, newY)
    }

    private fun processLeftMove(grid: Grid): Triple<Grid, Int, MutableList<Animation>> {
        val size = grid.size
        var totalScore = 0
        val allAnimations = mutableListOf<Animation>()
        val newCells = MutableList(size) { MutableList<Cell?>(size) { null } }

        for (row in 0 until size) {
            val originalRow = grid.cells[row]
            val (processedRow, score, animations) = processRowLeft(originalRow, row)
            totalScore += score
            allAnimations.addAll(animations)
            for (col in 0 until size) {
                newCells[row][col] = processedRow[col]
            }
        }

        // Fill any remaining null cells with empty cells
        val finalCells = newCells.mapIndexed { x, row ->
            row.mapIndexed { y, cell ->
                cell ?: Cell(Position(x, y), null, Random.nextLong())
            }
        }

        return Triple(Grid(size, finalCells), totalScore, allAnimations)
    }

    private fun processRowLeft(row: List<Cell>, rowIndex: Int): Triple<List<Cell?>, Int, MutableList<Animation>> {
        val nonNullCells = row.filter { it.value != null }
        val result = MutableList<Cell?>(row.size) { null }
        val animations = mutableListOf<Animation>()
        var score = 0
        var targetCol = 0
        var i = 0

        while (i < nonNullCells.size) {
            val currentCell = nonNullCells[i]

            if (i + 1 < nonNullCells.size && currentCell.value == nonNullCells[i + 1].value) {
                // Merge cells
                val newValue = currentCell.value!! * 2
                score += newValue
                val newPosition = Position(rowIndex, targetCol)
                val newCell = Cell(newPosition, newValue, Random.nextLong())
                result[targetCol] = newCell

                animations.add(
                    Animation.Merge(
                        sources = listOf(currentCell.position, nonNullCells[i + 1].position),
                        destination = newPosition,
                        newValue = newValue,
                        newCellId = newCell.id
                    ))

                i += 2
                targetCol++
            } else {
                // Move cell
                val newPosition = Position(rowIndex, targetCol)
                if (currentCell.position != newPosition) {
                    animations.add(
                        Animation.Move(
                            from = currentCell.position,
                            to = newPosition,
                            value = currentCell.value!!,
                            cellId = currentCell.id
                        ))
                }
                result[targetCol] = currentCell.copy(position = newPosition)
                i += 1
                targetCol++
            }
        }

        return Triple(result, score, animations)
    }
}