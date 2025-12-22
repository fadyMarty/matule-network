package com.fadymarty.network.data.remote

import com.fadymarty.network.data.remote.dto.AuthResponseDto
import com.fadymarty.network.domain.manager.AuthManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
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
                    authManager.saveSession(
                        token = authResponse.token,
                        userId = authResponse.record.id!!
                    )
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
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val json = Json { ignoreUnknownKeys = true }
        val matuleApi = Retrofit.Builder()
            .baseUrl(MatuleApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
            .create(MatuleApi::class.java)

        return matuleApi.refresh(token)
    }
}