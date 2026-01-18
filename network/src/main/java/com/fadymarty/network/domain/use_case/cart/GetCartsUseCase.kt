package com.fadymarty.network.domain.use_case.cart

import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.repository.MatuleRepository

class GetCartsUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(): Result<List<Cart>> {
        return matuleRepository.getCarts()
    }
}