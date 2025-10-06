package com.magni.game2048.di

import com.magni.game2048.feature.game.presentation.GameStateHolder
import com.magni.game2048.feature.game.useCases.CheckGameOverUseCase
import com.magni.game2048.feature.game.useCases.GetGameStateUseCase
import com.magni.game2048.feature.game.useCases.MakeMoveUseCase
import com.magni.game2048.feature.game.useCases.StartNewGameUseCase
import com.magni.game2048.feature.game.useCases.UndoMoveUseCase
import com.magni.game2048.feature.history.domain.useCase.ClearRecordsUseCase
import com.magni.game2048.feature.history.domain.useCase.GetBestScoreUseCase
import com.magni.game2048.feature.history.domain.useCase.GetRecordsFlowUseCase
import com.magni.game2048.feature.history.domain.useCase.GetRecordsUseCase
import com.magni.game2048.feature.history.domain.useCase.SaveRecordUseCase
import com.magni.game2048.feature.history.presentation.RecordsStateHolder
import com.magni.game2048.core.domain.useCase.GetSettingsFlowUseCase
import com.magni.game2048.feature.settings.domain.useCase.GetSettingsUseCase
import com.magni.game2048.feature.settings.domain.useCase.UpdateSettingsUseCase
import com.magni.game2048.feature.settings.presentation.SettingsStateHolder
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module


val gameModule = module {
    single<GameStateHolder> {
        GameStateHolder(
            makeMoveUseCase = get(),
            startNewGameUseCase = get(),
            undoMoveUseCase = get(),
            getGameStateUseCase = get(),
            getSettingsFlowUseCase = get()
        )
    }
    single<SettingsStateHolder> {
        SettingsStateHolder(
            getSettingsUseCase = get(),
            updateSettingsUseCase = get(),
            getSettingsFlowUseCase = get()
        )
    }
    single<RecordsStateHolder> {
        RecordsStateHolder(
            getRecordsUseCase = get(),
            clearRecordsUseCase = get(),
            getRecordsFlowUseCase = get()
        )
    }
}

val useCaseModule = module {
    // Game use cases
    factory { MakeMoveUseCase(get(), get()) }
    factory { StartNewGameUseCase(get(), get(), get()) }
    factory { UndoMoveUseCase(get()) }
    factory { GetGameStateUseCase(get()) }
    factory { CheckGameOverUseCase() }
    factory { SaveRecordUseCase(get(), get()) }

    // Settings use cases
    factory { GetSettingsUseCase(get()) }
    factory { UpdateSettingsUseCase(get()) }
    factory { GetSettingsFlowUseCase(get()) }

    // Records use cases
    factory { GetRecordsUseCase(get()) }
    factory { ClearRecordsUseCase(get()) }
    factory { GetBestScoreUseCase(get()) }
    factory { GetRecordsFlowUseCase(get()) }
}


val appModule = useCaseModule + gameModule