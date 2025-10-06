package com.magni.game2048.di

import com.magni.game2048.core.database.RealmGameRepository
import com.magni.game2048.core.database.RealmRecordsRepository
import com.magni.game2048.core.database.RealmSettingsRepository
import com.magni.game2048.core.domain.repository.GameRepository
import com.magni.game2048.feature.game.presentation.GameViewModel
import com.magni.game2048.feature.game.useCases.RandomTileGenerator
import com.magni.game2048.feature.game.useCases.TileGenerator
import com.magni.game2048.feature.history.presentation.RecordsViewModel
import com.magni.game2048.core.domain.repository.RecordsRepository
import com.magni.game2048.feature.settings.SettingsViewModel
import com.magni.game2048.core.domain.repository.SettingsRepository
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val androidViewModelModule = module {
    viewModel { GameViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { RecordsViewModel(get()) }
}

val androidRepositoryModule = module {
    single<GameRepository> { RealmGameRepository() }
    single<SettingsRepository> { RealmSettingsRepository() }
    single<RecordsRepository> { RealmRecordsRepository() }
    single<TileGenerator> { RandomTileGenerator() }
}

val androidAppModule = androidRepositoryModule + androidViewModelModule