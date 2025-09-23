package com.magni.game2048.core.database

import com.magni.game2048.core.database.entity.RealmCell
import com.magni.game2048.core.database.entity.RealmGame
import com.magni.game2048.core.database.entity.RealmGrid
import com.magni.game2048.core.database.entity.RealmMoveResult
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import javax.naming.Context

object RealmManager {
    private var realm: Realm? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    fun getRealm(): Realm {
        if (realm != null || realm?.isClosed() == true) {
            return realm!!
        } else synchronized(this) {
            if (realm == null || realm!!.isClosed()) {
                realm = Realm.open(
                    RealmConfiguration.Builder(
                        schema = setOf(
                            RealmGame::class,
                            RealmGrid::class,
                            RealmCell::class,
                            RealmMoveResult::class
                        )
                    ).build()
                )
            }
            return realm!!
        }
    }

    fun closeRealm() {
        synchronized(this) {
            realm?.close()
            realm = null
        }
    }

    suspend fun <T> withRealm(block: suspend (Realm) -> T): T {
        return coroutineScope {
            val realmInstance = getRealm()
            block(realmInstance)
        }
    }

    suspend fun <T> writeRealm(block: (MutableRealm) -> T): T {
        return coroutineScope {
            val realmInstance = getRealm()
            realmInstance.write {
                block(this)
            }
        }
    }
}