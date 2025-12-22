package com.fadymarty.network.data.manager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fadymarty.network.di.dataStore
import com.fadymarty.network.domain.manager.AuthManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthManagerImpl(
    private val context: Context,
) : AuthManager {
    companion object {
        private val TOKEN = stringPreferencesKey("token")
        private val USER_ID = stringPreferencesKey("user_id")
        private val PIN = stringPreferencesKey("pin")
    }

    override suspend fun saveSession(token: String, userId: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN] = token
            preferences[USER_ID] = userId
        }
    }

    override fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN]
        }
    }

    override fun getUserId(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID]
        }
    }

    override suspend fun savePin(pin: String) {
        context.dataStore.edit { preferences ->
            preferences[PIN] = pin
        }
    }

    override fun getPin(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[PIN]
        }
    }

    override suspend fun clearSession() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN)
            preferences.remove(USER_ID)
            preferences.remove(PIN)
        }
    }
}