package com.magni.game2048.core.database.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmRecord : RealmObject {
    @PrimaryKey
    var id: String = ""
    var score: Int = 0
    var maxTile: Int = 0
    var date: Long = 0
    var difficulty: String = ""
    var playerName: String = ""
}