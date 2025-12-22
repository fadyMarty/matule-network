package com.fadymarty.network.domain.manager

import kotlinx.coroutines.flow.Flow

interface AuthManager {
    suspend fun saveSession(token: String, userId: String)
    fun getToken(): Flow<String?>
    fun getUserId(): Flow<String?>
    suspend fun savePin(pin: String)
    fun getPin(): Flow<String?>
    suspend fun clearSession()
}