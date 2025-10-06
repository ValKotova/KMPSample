package com.magni.game2048.core.database.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmGrid : EmbeddedRealmObject {
    var size: Int = 0
    var cells: RealmList<RealmCell> = realmListOf()
}
