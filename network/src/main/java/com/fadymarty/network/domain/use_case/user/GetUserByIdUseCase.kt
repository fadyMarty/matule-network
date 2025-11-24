package com.fadymarty.network.domain.use_case.user

import com.fadymarty.network.domain.model.User
import com.fadymarty.network.domain.repository.MatuleRepository

class GetUserByIdUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(id: String): Result<User> {
        return matuleRepository.getUserById(id)
    }
}