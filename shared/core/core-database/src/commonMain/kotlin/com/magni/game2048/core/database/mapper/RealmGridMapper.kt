package com.magni.game2048.core.database.mapper

import com.magni.game2048.core.database.entity.RealmCell
import com.magni.game2048.core.database.entity.RealmGrid
import com.magni.game2048.core.domain.entity.Cell
import com.magni.game2048.core.domain.entity.Grid
import com.magni.game2048.core.domain.entity.Position
import io.realm.kotlin.ext.realmListOf
import kotlin.random.Random

class RealmGridMapper(private val realmCellMapper: RealmCellMapper = RealmCellMapper()) {
    fun toRealmGrid(grid: Grid): RealmGrid {
        val realmCells = realmListOf<RealmCell>()
        grid.cells.forEach { row ->
            row.forEach { cell ->
                realmCells.add(realmCellMapper.toRealmCell(cell))
            }
        }
        return RealmGrid(grid.size, realmCells)
    }

    fun toGrid(realmGrid: RealmGrid): Grid {
        val cells2D = List(realmGrid.size) { x ->
            List(realmGrid.size) { y ->
                realmGrid.cells.find { it.x == x && it.y == y }?.let { realmCell ->
                    realmCellMapper.toCell(realmCell)
                } ?: Cell(Position(x, y), null, Random.nextLong())
            }
        }
        return Grid(realmGrid.size, cells2D)
    }
}