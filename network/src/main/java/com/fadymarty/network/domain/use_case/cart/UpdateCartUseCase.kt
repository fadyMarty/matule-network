package com.fadymarty.network.domain.use_case.cart

import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.repository.MatuleRepository

class UpdateCartUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(cart: Cart): Result<Cart> {
        return matuleRepository.updateCart(cart)
    }
}