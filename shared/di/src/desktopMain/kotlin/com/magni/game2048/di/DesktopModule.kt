package com.magni.game2048.di

import com.magni.game2048.core.data.InMemoryGameRepository
import com.magni.game2048.core.data.InMemoryRecordsRepository
import com.magni.game2048.core.data.InMemorySettingsRepository
import com.magni.game2048.core.domain.repository.GameRepository
import com.magni.game2048.core.domain.repository.RecordsRepository
import com.magni.game2048.core.domain.repository.SettingsRepository
import com.magni.game2048.feature.game.useCases.RandomTileGenerator
import com.magni.game2048.feature.game.useCases.TileGenerator
import org.koin.dsl.module


val desktopRepositoryModule = module {
    single<GameRepository> { InMemoryGameRepository() }
    single<SettingsRepository> { InMemorySettingsRepository() }
    single<RecordsRepository> { InMemoryRecordsRepository() }
    single<TileGenerator> { RandomTileGenerator() }
}

val desktopAppModule = desktopRepositoryModule