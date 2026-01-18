package com.fadymarty.network.domain.use_case.user

import com.fadymarty.network.domain.model.User
import com.fadymarty.network.domain.repository.MatuleRepository

class GetUsersUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(): Result<List<User>> {
        return matuleRepository.getUsers()
    }
}