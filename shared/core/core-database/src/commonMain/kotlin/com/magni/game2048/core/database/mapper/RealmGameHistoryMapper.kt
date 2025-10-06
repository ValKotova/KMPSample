package com.magni.game2048.core.database.mapper

import com.magni.game2048.core.database.entity.RealmCell
import com.magni.game2048.core.database.entity.RealmGameHistory
import com.magni.game2048.core.database.entity.RealmGrid
import com.magni.game2048.core.domain.entity.Cell
import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.Grid
import com.magni.game2048.core.domain.entity.Position
import io.realm.kotlin.ext.realmListOf
import kotlin.random.Random

class RealmGameHistoryMapper {
    fun toRealmGameHistory(game: Game, historyId: String): RealmGameHistory {
        return RealmGameHistory().apply {
            this.historyId = historyId
            this.gameId = game.id
            this.score = game.score
            this.maxTile = game.maxTile
            this.isGameOver = game.isGameOver
            this.difficulty = game.difficulty.name
            this.createdAt = System.currentTimeMillis()
            this.grid = toRealmGrid(game.grid)
        }
    }

    fun toGame(realmGameHistory: RealmGameHistory): Game {
        return Game(
            id = realmGameHistory.gameId,
            grid = toGrid(realmGameHistory.grid!!),
            score = realmGameHistory.score,
            maxTile = realmGameHistory.maxTile,
            isGameOver = realmGameHistory.isGameOver,
            difficulty = enumValueOf(realmGameHistory.difficulty)
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