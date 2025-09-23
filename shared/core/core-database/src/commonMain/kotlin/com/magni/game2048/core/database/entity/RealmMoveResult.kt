package com.magni.game2048.core.database.entity

import io.realm.kotlin.types.RealmObject

open class RealmMoveResult : RealmObject {
    var scoreDelta: Int = 0
    var grid: RealmGrid? = null

    constructor()

    constructor(scoreDelta: Int, grid: RealmGrid) : this() {
        this.scoreDelta = scoreDelta
        this.grid = grid
    }
}