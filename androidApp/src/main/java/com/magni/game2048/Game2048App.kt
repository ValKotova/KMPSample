package com.magni.game2048

import android.app.Application
import com.magni.game2048.di.androidViewModelModule
import com.magni.game2048.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Game2048App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Game2048App)
            modules(appModule + androidViewModelModule)
        }
    }
}