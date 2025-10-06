package com.magni.game2048.core.database.entity

import io.realm.kotlin.types.EmbeddedRealmObject

class RealmCell : EmbeddedRealmObject {
    var x: Int = 0
    var y: Int = 0
    var value: Int? = null
    var cellId: Long = 0
}