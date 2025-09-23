package com.magni.game2048.core.database.mapper

import com.magni.game2048.core.database.entity.RealmGame
import com.magni.game2048.core.database.entity.RealmGrid
import com.magni.game2048.core.domain.entity.Difficulty
import com.magni.game2048.core.domain.entity.Game
import io.realm.kotlin.ext.realmListOf

class RealmGameMapper(private val realmGridMapper: RealmGridMapper = RealmGridMapper()) {
    fun toRealmGame(game: Game): RealmGame {
        return RealmGame(
            id = game.id,
            score = game.score,
            maxTile = game.maxTile,
            isGameOver = game.isGameOver,
            difficulty = game.difficulty.name,
            grid = realmGridMapper.toRealmGrid(game.grid)
        )
    }

    fun toGame(realmGame: RealmGame): Game {
        return Game(
            id = realmGame.id,
            grid = realmGridMapper.toGrid(realmGame.grid ?: createEmptyRealmGrid()),
            score = realmGame.score,
            maxTile = realmGame.maxTile,
            isGameOver = realmGame.isGameOver,
            difficulty = Difficulty.valueOf(realmGame.difficulty)
        )
    }

    private fun createEmptyRealmGrid(): RealmGrid {
        return RealmGrid(4, realmListOf())
    }
}