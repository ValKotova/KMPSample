package com.magni.game2048.di

import com.magni.game2048.core.data.GameRepositoryFactory
import com.magni.game2048.feature.game.presentation.GameStateHolder
import com.magni.game2048.core.domain.repository.GameRepository
import com.magni.game2048.feature.game.common.Navigator
import com.magni.game2048.feature.game.useCases.CheckGameOverUseCase
import com.magni.game2048.feature.game.useCases.GetGameStateUseCase
import com.magni.game2048.feature.game.useCases.MakeMoveUseCase
import com.magni.game2048.feature.game.useCases.RandomTileGenerator
import com.magni.game2048.feature.game.useCases.StartNewGameUseCase
import com.magni.game2048.feature.game.useCases.TileGenerator
import com.magni.game2048.feature.game.useCases.UndoMoveUseCase
import com.magni.game2048.feature.settings.data.SettingsRepositoryImpl
import com.magni.game2048.feature.settings.repo.SettingsRepository
import org.koin.dsl.module

val appModule = module {

    single<Navigator> { Navigator() }

    single<GameStateHolder> {
        GameStateHolder(
            makeMoveUseCase = get(),
            startNewGameUseCase = get(),
            undoMoveUseCase = get(),
            getGameStateUseCase = get(),
            navigator = get()
        )
    }

    single<GameRepository> {
        val useRealm = true // Configurable via platform-specific settings
        GameRepositoryFactory().createRepository(useRealm)
    }
    single<SettingsRepository> { SettingsRepositoryImpl() }
    single<TileGenerator> { RandomTileGenerator() }
    factory { MakeMoveUseCase(get(), get()) }
    factory { StartNewGameUseCase(get(), get()) }
    factory { UndoMoveUseCase(get()) }
    factory { CheckGameOverUseCase() }
    factory { GetGameStateUseCase(get()) }
}