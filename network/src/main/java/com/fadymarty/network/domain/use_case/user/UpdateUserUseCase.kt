package com.fadymarty.network.domain.use_case.user

import com.fadymarty.network.domain.model.User
import com.fadymarty.network.domain.repository.MatuleRepository

class UpdateUserUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(user: User): Result<User> {
        return matuleRepository.updateUser(user)
    }
}