package com.fadymarty.network.domain.use_case.bucket

import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.repository.MatuleRepository

class CreateCartUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(cart: Cart): Result<Cart> {
        return matuleRepository.createCart(cart)
    }
}