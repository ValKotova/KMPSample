package com.magni.game2048.core.database.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

open class RealmGrid : RealmObject {
    var size: Int = 0
    var cells: RealmList<RealmCell> = realmListOf()

    constructor()

    constructor(size: Int, cells: RealmList<RealmCell>) : this() {
        this.size = size
        this.cells = cells
    }
}
