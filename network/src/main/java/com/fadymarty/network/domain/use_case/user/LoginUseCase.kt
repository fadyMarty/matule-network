package com.fadymarty.network.domain.use_case.user

import com.fadymarty.network.domain.model.AuthResponse
import com.fadymarty.network.domain.repository.MatuleRepository

class LoginUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthResponse> {
        return matuleRepository.login(email, password)
    }
}