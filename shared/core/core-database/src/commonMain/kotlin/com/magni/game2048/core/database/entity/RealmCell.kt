package com.magni.game2048.core.database.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class RealmCell : RealmObject {
    var x: Int = 0
    var y: Int = 0
    var value: Int? = null

    @PrimaryKey
    var id: Long = 0

    constructor()

    constructor(x: Int, y: Int, value: Int?, id: Long) {
        this.x = x
        this.y = y
        this.value = value
        this.id = id
    }
}