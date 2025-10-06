package com.magni.game2048.core.database

import com.magni.game2048.core.database.entity.RealmSettings
import com.magni.game2048.core.database.mapper.RealmSettingsMapper
import com.magni.game2048.core.domain.entity.AppSettings
import com.magni.game2048.core.domain.entity.AppTheme
import com.magni.game2048.core.domain.entity.Difficulty
import com.magni.game2048.core.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RealmSettingsRepository(
    private val realmSettingsMapper: RealmSettingsMapper = RealmSettingsMapper()
) : SettingsRepository {

    private val _settingsFlow = MutableStateFlow<AppSettings>(getDefaultSettings())

    init {
        CoroutineScope(Dispatchers.Default).launch {
            loadSettings()
        }
    }

    override suspend fun loadSettings(): AppSettings {
        return RealmManager.withRealm { realm ->
            val realmSettings = realm.query(RealmSettings::class).find().firstOrNull()
            realmSettings?.let { realmSettingsMapper.toAppSettings(it) } ?: getDefaultSettings()
        }.also { settings ->
            _settingsFlow.value = settings
        }
    }

    override suspend fun saveSettings(settings: AppSettings) {
        RealmManager.writeRealm { mutableRealm ->
            mutableRealm.delete(RealmSettings::class)

            val realmSettings = realmSettingsMapper.toRealmSettings(settings)
            mutableRealm.copyToRealm(realmSettings)
        }
        _settingsFlow.value = settings
    }

    private fun getDefaultSettings(): AppSettings {
        return AppSettings(
            playerName = "Player",
            difficulty = Difficulty.MEDIUM,
            soundEnabled = true,
            musicEnabled = true,
            theme = AppTheme.SYSTEM,
            gridSize = 4
        )
    }

    override fun getSettingsFlow(): StateFlow<AppSettings> = _settingsFlow
}