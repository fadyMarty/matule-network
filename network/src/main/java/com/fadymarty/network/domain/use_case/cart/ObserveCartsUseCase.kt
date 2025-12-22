package com.fadymarty.network.domain.use_case.cart

import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.repository.MatuleRepository
import kotlinx.coroutines.flow.Flow

class ObserveCartsUseCase(
    private val matuleRepository: MatuleRepository,
) {
    operator fun invoke(): Flow<List<Cart>> {
        return matuleRepository.cartsFlow
    }
}