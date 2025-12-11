package com.fadymarty.network.data.remote

import com.fadymarty.network.domain.manager.AuthManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class MatuleInterceptor(
    private val authManager: AuthManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            authManager.getToken().first()
        }
        val request = chain.request().newBuilder()
        token?.let {
            request.addHeader("Authorization", it)
        }
        return chain.proceed(request.build())
    }
}