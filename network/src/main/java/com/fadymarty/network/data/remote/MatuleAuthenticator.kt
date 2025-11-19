package com.fadymarty.network.data.remote

import com.fadymarty.network.data.remote.dto.AuthResponseDto
import com.fadymarty.network.domain.manager.AuthManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MatuleAuthenticator(
    private val authManager: AuthManager,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking {
            authManager.getToken().first()
        }
        return runBlocking {
            try {
                token?.let {
                    val authResponse = refresh(it)
                    authResponse.record.id?.let { id ->
                        authManager.saveSession(
                            token = authResponse.token,
                            userId = id
                        )
                    }
                    response.request.newBuilder()
                        .addHeader("Authorization", authResponse.token)
                        .build()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                authManager.clearSession()
                null
            }
        }
    }

    private suspend fun refresh(token: String): AuthResponseDto {
        val json = Json { ignoreUnknownKeys = true }

        val matuleApi = Retrofit.Builder()
            .baseUrl(MatuleApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MatuleApi::class.java)

        return matuleApi.refresh(token)
    }
}