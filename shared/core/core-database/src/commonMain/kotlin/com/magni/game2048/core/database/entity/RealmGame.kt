package com.magni.game2048.core.database.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class RealmGame : RealmObject {
    @PrimaryKey
    var id: String = ""
    var score: Int = 0
    var maxTile: Int = 0
    var isGameOver: Boolean = false
    var difficulty: String = ""
    var grid: RealmGrid? = null

    constructor()

    constructor(
        id: String,
        score: Int,
        maxTile: Int,
        isGameOver: Boolean,
        difficulty: String,
        grid: RealmGrid
    ) : this() {
        this.id = id
        this.score = score
        this.maxTile = maxTile
        this.isGameOver = isGameOver
        this.difficulty = difficulty
        this.grid = grid
    }
}