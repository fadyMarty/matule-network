package com.fadymarty.network.domain.use_case.user

import com.fadymarty.network.domain.manager.AuthManager
import kotlinx.coroutines.flow.Flow

class GetUserIdUseCase(
    private val authManager: AuthManager,
) {
    fun invoke(): Flow<String?> {
        return authManager.getUserId()
    }
}