package com.fadymarty.network.domain.use_case.shop

import com.fadymarty.network.domain.model.Product
import com.fadymarty.network.domain.repository.MatuleRepository

class GetProductByIdUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(id: String): Result<Product> {
        return matuleRepository.getProductById(id)
    }
}