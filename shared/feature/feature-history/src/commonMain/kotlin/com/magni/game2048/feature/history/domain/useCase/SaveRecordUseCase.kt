package com.magni.game2048.feature.history.domain.useCase

import com.magni.game2048.core.domain.entity.Game
import com.magni.game2048.core.domain.entity.GameRecord
import com.magni.game2048.core.domain.repository.RecordsRepository
import com.magni.game2048.core.domain.repository.SettingsRepository
import kotlin.random.Random

class SaveRecordUseCase(
    private val recordsRepository: RecordsRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(game: Game) {
        val settings = settingsRepository.loadSettings()
        val record = GameRecord(
            id = Random.nextLong().toString(),
            score = game.score,
            maxTile = game.maxTile,
            date = System.currentTimeMillis(),
            difficulty = game.difficulty,
            playerName = settings.playerName
        )
        recordsRepository.saveRecord(record)
        println("GAME_DEBUG: SaveRecordUseCase - Saved record for player: ${settings.playerName}, score: ${game.score}")
    }
}