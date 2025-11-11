package com.fadymarty.network.domain.manager

import kotlinx.coroutines.flow.Flow

interface AuthManager {
    suspend fun saveSession(token: String, userId: String)
    suspend fun getToken(): Flow<String?>
    suspend fun getUserId(): Flow<String?>
    suspend fun clearSession()
}