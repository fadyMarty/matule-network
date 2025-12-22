package com.fadymarty.network.domain.use_case.user

import com.fadymarty.network.domain.manager.AuthManager

class ClearSessionUseCase(
    private val authManager: AuthManager,
) {
    suspend operator fun invoke() {
        authManager.clearSession()
    }
}