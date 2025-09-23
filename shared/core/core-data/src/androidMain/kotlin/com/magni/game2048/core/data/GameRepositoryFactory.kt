package com.magni.game2048.core.data

import com.magni.game2048.core.domain.repository.GameRepository

actual class GameRepositoryFactory() {
    actual fun createRepository(useRealm: Boolean): GameRepository {
        return if (useRealm) {
            RealmGameRepository()
        } else {
            InMemoryGameRepository()
        }
    }
}