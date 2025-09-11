package com.magni.game2048.feature.game

import com.magni.game2048.feature.game.entity.Animation
import com.magni.game2048.feature.game.entity.Cell
import com.magni.game2048.feature.settings.entity.Difficulty
import com.magni.game2048.feature.game.entity.Grid
import com.magni.game2048.feature.game.entity.Position
import com.magni.game2048.feature.game.useCases.TileGenerator
import org.junit.Assert.assertEquals

// Helper function to create a readable grid
fun createGrid(size: Int, values: List<Int?>): Grid {
    val cells = mutableListOf<List<Cell>>()
    for (x in 0 until size) {
        val row = mutableListOf<Cell>()
        for (y in 0 until size) {
            val index = x * size + y
            val value = if (index < values.size) values[index] else null
            row.add(Cell(Position(x, y), value, (x * size + y).toLong()))
        }
        cells.add(row)
    }
    return Grid(size, cells)
}

// Helper function to convert grid to readable format
fun printGrid(grid: Grid) {
    for (x in 0 until grid.size) {
        for (y in 0 until grid.size) {
            val value = grid.cells[x][y].value
            print("${value ?: "."}\t")
        }
        println()
    }
    println()
}

fun assertGridEquals(expected: Grid, actual: Grid) {
    assertEquals("Grid size mismatch", expected.size, actual.size)

    val errors = mutableListOf<String>()
    for (x in 0 until expected.size) {
        for (y in 0 until expected.size) {
            val expectedValue = expected.cells[x][y].value
            val actualValue = actual.cells[x][y].value
            if (expectedValue != actualValue) {
                errors.add("At position ($x, $y): expected $expectedValue but got $actualValue")
            }
        }
    }

    if (errors.isNotEmpty()) {
        // Print both grids for debugging
        println("Expected grid:")
        printGrid(expected)
        println("Actual grid:")
        printGrid(actual)

        // Fail with all errors
        throw AssertionError("Grids don't match:\n" + errors.joinToString("\n"))
    }
}

fun printAnimations(animations: List<Animation>) {
    println("Animations (${animations.size}):")
    animations.forEachIndexed { index, animation ->
        when (animation) {
            is Animation.Move -> println("  $index: Move from ${animation.from} to ${animation.to} value ${animation.value}")
            is Animation.Merge -> println("  $index: Merge at ${animation.sources} to value ${animation.newValue}")
            is Animation.Appear -> println("  $index: Appear at ${animation.at} value ${animation.value}")
        }
    }
}

fun assertPositionsEqual(message: String, expected: List<Position>, actual: List<Position>) {
    assertEquals("$message: Number of positions should match", expected.size, actual.size)

    expected.zip(actual).forEach { (exp, act) ->
        assertEquals("$message: X coordinate should match", exp.x, act.x)
        assertEquals("$message: Y coordinate should match", exp.y, act.y)
    }
}

class TestTileGenerator(private val nextTile: Pair<Position, Int>? = null) :
    TileGenerator {
    override fun generateTile(grid: Grid, difficulty: Difficulty): Pair<Position, Int>? {
        return nextTile
    }
}