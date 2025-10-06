package com.magni.game2048.core.database.entity

fun generateCellId(gridId: String, x: Int, y: Int): Long {
    val baseId = "${gridId}_${x}_${y}".hashCode().toLong()
    return baseId + (x * 1000L) + (y * 1000000L)
}