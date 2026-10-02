package com.segaud.learnqa.data.progress

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.booleanPreferencesKey

private val Context.dataStore by preferencesDataStore(
    name = "progress"
)

class ProgressRepository(
    private val context: Context
) {

    private object Keys {
        val TOTAL_XP = intPreferencesKey("total_xp")
    }

    val totalXp: Flow<Int> =
        context.dataStore.data.map { preferences ->
            preferences[Keys.TOTAL_XP] ?: 0
        }

    suspend fun addXp(amount: Int) {
        context.dataStore.edit { preferences ->

            val currentXp =
                preferences[Keys.TOTAL_XP] ?: 0

            preferences[Keys.TOTAL_XP] =
                currentXp + amount
        }
    }
}