package com.magni.game2048.core.database.mapper

import com.magni.game2048.core.database.entity.RealmCell
import com.magni.game2048.core.database.entity.RealmGame
import com.magni.game2048.core.database.entity.RealmGrid
import com.magni.game2048.core.domain.entity.Cell
import com.magni.game2048.core.domain.entity.Difficulty
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.Grid
import com.magni.game2048.core.domain.entity.Position
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.realmListOf
import kotlin.random.Random

class RealmGameMapper {
    fun toRealmGame(game: Game): RealmGame {
        return RealmGame().apply {
            id = game.id
            score = game.score
            maxTile = game.maxTile
            isGameOver = game.isGameOver
            difficulty = game.difficulty.name
            grid = toRealmGrid(game.grid)
        }
    }

    fun toGame(realmGame: RealmGame): Game {
        return Game(
            id = realmGame.id,
            grid = toGrid(realmGame.grid!!),
            score = realmGame.score,
            maxTile = realmGame.maxTile,
            isGameOver = realmGame.isGameOver,
            difficulty = enumValueOf(realmGame.difficulty)
        )
    }

    private fun toRealmGrid(grid: Grid): RealmGrid {
        return RealmGrid().apply {
            size = grid.size
            cells = realmListOf<RealmCell>().apply {
                grid.cells.forEach { row ->
                    row.forEach { cell ->
                        add(toRealmCell(cell))
                    }
                }
            }
        }
    }

    private fun toGrid(realmGrid: RealmGrid): Grid {
        val cells2D = List(realmGrid.size) { x ->
            List(realmGrid.size) { y ->
                realmGrid.cells.find { it.x == x && it.y == y }?.let { realmCell ->
                    toCell(realmCell)
                } ?: Cell(Position(x, y), null, Random.nextLong())
            }
        }
        return Grid(realmGrid.size, cells2D)
    }

    private fun toRealmCell(cell: Cell): RealmCell {
        return RealmCell().apply {
            x = cell.position.x
            y = cell.position.y
            value = cell.value
            cellId = cell.id
        }
    }

    private fun toCell(realmCell: RealmCell): Cell {
        return Cell(
            position = Position(realmCell.x, realmCell.y),
            value = realmCell.value,
            id = realmCell.cellId
        )
    }
}