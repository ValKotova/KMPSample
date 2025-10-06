package com.magni.game2048.core.database.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmGameHistory : RealmObject {
    @PrimaryKey
    var historyId: String = ""
    var gameId: String = ""
    var score: Int = 0
    var maxTile: Int = 0
    var isGameOver: Boolean = false
    var difficulty: String = ""
    var createdAt: Long = System.currentTimeMillis()

    var grid: RealmGrid? = null
}
