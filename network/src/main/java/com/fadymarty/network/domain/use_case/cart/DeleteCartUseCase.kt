package com.fadymarty.network.domain.use_case.cart

import com.fadymarty.network.domain.repository.MatuleRepository

class DeleteCartUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return matuleRepository.deleteCart(id)
    }
}