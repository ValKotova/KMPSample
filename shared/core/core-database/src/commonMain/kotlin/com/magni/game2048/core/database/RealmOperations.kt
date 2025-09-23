package com.magni.game2048.core.database

import io.realm.kotlin.MutableRealm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.types.RealmObject
import kotlin.reflect.KClass

interface RealmOperations {
    suspend fun <T : RealmObject> query(clazz: KClass<T>): List<T>
    suspend fun <T : RealmObject> save(entity: T, updatePolicy: UpdatePolicy = UpdatePolicy.ALL)
    suspend fun <T : RealmObject> delete(clazz: KClass<T>)
    suspend fun <T> transaction(block: (MutableRealm) -> T): T
}

class RealmOperationsImpl : RealmOperations {
    override suspend fun <T : RealmObject> query(clazz: KClass<T>): List<T> {
        return RealmManager.withRealm { realm ->
            realm.query(clazz).find()
        }
    }

    override suspend fun <T : RealmObject> save(entity: T, updatePolicy: UpdatePolicy) {
        RealmManager.writeRealm { mutableRealm ->
            mutableRealm.copyToRealm(entity, updatePolicy)
        }
    }

    override suspend fun <T : RealmObject> delete(clazz: KClass<T>) {
        RealmManager.writeRealm { mutableRealm ->
            mutableRealm.delete(mutableRealm.query(clazz))
        }
    }

    override suspend fun <T> transaction(block: (MutableRealm) -> T): T {
        return RealmManager.writeRealm(block)
    }
}