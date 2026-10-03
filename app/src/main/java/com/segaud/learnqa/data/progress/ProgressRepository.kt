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
        
    fun lessonBestXp(
        lessonId: String
    ): Flow<Int> {

        val bestXpKey =
            intPreferencesKey("lesson_${lessonId}_best_xp")

        return context.dataStore.data.map { preferences ->
            preferences[bestXpKey] ?: 0
        }
    }

    fun isLessonCompleted(
        lessonId: String
    ): Flow<Boolean> {

        val completedKey =
            booleanPreferencesKey("lesson_${lessonId}_completed")

        return context.dataStore.data.map { preferences ->
            preferences[completedKey] ?: false
        }
    }

    suspend fun recordLessonResult(
        lessonId: String,
        xpEarned: Int
    ): Int {

        var xpAwarded = 0

        context.dataStore.edit { preferences ->

            val bestXpKey =
                intPreferencesKey("lesson_${lessonId}_best_xp")

            val completedKey =
                booleanPreferencesKey("lesson_${lessonId}_completed")

            val previousBest =
                preferences[bestXpKey] ?: 0

            if (xpEarned > previousBest) {

                xpAwarded = xpEarned - previousBest

                preferences[bestXpKey] = xpEarned

                val currentTotal =
                    preferences[Keys.TOTAL_XP] ?: 0

                preferences[Keys.TOTAL_XP] =
                    currentTotal + xpAwarded
            }

            preferences[completedKey] = true
        }

        return xpAwarded
    }
    
    fun areLessonsCompleted(
        lessonIds: List<String>
    ): Flow<Boolean> {

        return context.dataStore.data.map { preferences ->

            lessonIds.all { lessonId ->

                val completedKey =
                    booleanPreferencesKey(
                        "lesson_${lessonId}_completed"
                    )

                preferences[completedKey] ?: false
            }
        }
    }
}