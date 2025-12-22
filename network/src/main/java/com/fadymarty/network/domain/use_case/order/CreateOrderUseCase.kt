package com.fadymarty.network.domain.use_case.order

import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.repository.MatuleRepository

class CreateOrderUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(bucket: List<Cart>): Result<Unit> {
        return matuleRepository.createOrder(bucket)
    }
}