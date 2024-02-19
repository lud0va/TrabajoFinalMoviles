package com.example.apolloproject.utils

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.apolloproject.data.apollo.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreTokens @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val accessToken = stringPreferencesKey("accesstoken")
        private val refreshToken = stringPreferencesKey("refreshtoken")
    }

    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[accessToken]
        }
    }


    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[accessToken] = token
        }
    }


    fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[refreshToken]
        }
    }

    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[refreshToken] = token
        }
    }


}