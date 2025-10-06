package com.magni.game2048.core.database.mapper

import com.magni.game2048.core.database.entity.RealmSettings
import com.magni.game2048.core.domain.entity.AppSettings

class RealmSettingsMapper {

    fun toRealmSettings(settings: AppSettings): RealmSettings {
        return RealmSettings().apply {
            id = "settings"
            playerName = settings.playerName
            difficulty = settings.difficulty.name
            soundEnabled = settings.soundEnabled
            musicEnabled = settings.musicEnabled
            theme = settings.theme.name
            gridSize = settings.gridSize
        }
    }

    fun toAppSettings(realmSettings: RealmSettings): AppSettings {
        return AppSettings(
            playerName = realmSettings.playerName,
            difficulty = enumValueOf(realmSettings.difficulty),
            soundEnabled = realmSettings.soundEnabled,
            musicEnabled = realmSettings.musicEnabled,
            theme = enumValueOf(realmSettings.theme),
            gridSize = realmSettings.gridSize
        )
    }
}