package com.magni.game2048.core.data

import com.magni.game2048.core.domain.repository.GameRepository

expect class GameRepositoryFactory {
    fun createRepository(useRealm: Boolean = true): GameRepository
}