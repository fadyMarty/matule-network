package com.fadymarty.network.domain.use_case.user

import com.fadymarty.network.domain.manager.AuthManager

class SavePinUseCase(
    private val authManager: AuthManager,
) {
    suspend operator fun invoke(pin: String) {
        authManager.savePin(pin)
    }
}