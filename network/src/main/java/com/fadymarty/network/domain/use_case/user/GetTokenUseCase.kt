package com.fadymarty.network.domain.use_case.user

import com.fadymarty.network.domain.manager.AuthManager
import kotlinx.coroutines.flow.Flow

class GetTokenUseCase(
    private val authManager: AuthManager,
) {
    operator fun invoke(): Flow<String?> {
        return authManager.getToken()
    }
}