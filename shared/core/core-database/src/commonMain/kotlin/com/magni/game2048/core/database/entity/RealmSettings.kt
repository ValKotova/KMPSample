package com.magni.game2048.core.database.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmSettings : RealmObject {
    @PrimaryKey
    var id: String = ""
    var playerName: String = ""
    var difficulty: String = ""
    var soundEnabled: Boolean = true
    var musicEnabled: Boolean = true
    var theme: String = ""
    var gridSize: Int = 4
}