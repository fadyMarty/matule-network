package com.fadymarty.network.domain.use_case.pin

import com.fadymarty.network.domain.manager.AuthManager
import kotlinx.coroutines.flow.Flow

class GetPinUseCase(
    private val authManager: AuthManager,
) {
    operator fun invoke(): Flow<String?> {
        return authManager.getPin()
    }
}