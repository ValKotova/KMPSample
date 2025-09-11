package com.magni.game2048.di

import com.magni.game2048.feature.game.data.GameRepositoryImpl
import com.magni.game2048.feature.game.presentation.GameStateHolder
import com.magni.game2048.feature.game.repo.GameRepository
import com.magni.game2048.feature.game.useCases.MakeMoveUseCase
import com.magni.game2048.feature.game.useCases.RandomTileGenerator
import com.magni.game2048.feature.game.useCases.StartNewGameUseCase
import com.magni.game2048.feature.game.useCases.TileGenerator
import com.magni.game2048.feature.settings.data.SettingsRepositoryImpl
import com.magni.game2048.feature.settings.repo.SettingsRepository
import org.koin.dsl.module

val appModule = module {
    single<GameRepository> { GameRepositoryImpl() }
    single<SettingsRepository> { SettingsRepositoryImpl() }
    single<TileGenerator> { RandomTileGenerator() }
    factory { MakeMoveUseCase(get(), get()) }
    factory { StartNewGameUseCase(get(), get()) }
    factory { GameStateHolder(get(), get()) }
}