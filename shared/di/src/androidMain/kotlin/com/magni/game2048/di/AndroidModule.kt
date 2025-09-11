package com.magni.game2048.di

import com.magni.game2048.feature.game.presentation.AndroidGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val androidViewModelModule = module {
    viewModel { AndroidGameViewModel(get()) }
}