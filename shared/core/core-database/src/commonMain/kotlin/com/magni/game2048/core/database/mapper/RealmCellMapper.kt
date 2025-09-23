package com.magni.game2048.core.database.mapper

import com.magni.game2048.core.database.entity.RealmCell
import com.magni.game2048.core.domain.entity.Cell
import com.magni.game2048.core.domain.entity.Position

class RealmCellMapper {
    fun toRealmCell(cell: Cell): RealmCell {
        return RealmCell(
            x = cell.position.x,
            y = cell.position.y,
            value = cell.value,
            id = cell.id
        )
    }

    fun toCell(realmCell: RealmCell): Cell {
        return Cell(
            position = Position(realmCell.x, realmCell.y),
            value = realmCell.value,
            id = realmCell.id
        )
    }
}