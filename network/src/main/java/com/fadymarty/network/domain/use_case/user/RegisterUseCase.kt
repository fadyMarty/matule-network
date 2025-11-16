package com.fadymarty.network.domain.use_case.user

import com.fadymarty.network.domain.model.User
import com.fadymarty.network.domain.repository.MatuleRepository

class RegisterUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(user: User): Result<Unit> {
        return matuleRepository.register(user)
    }
}