package com.fadymarty.network.domain.use_case.bucket

import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.model.Product
import com.fadymarty.network.domain.repository.MatuleRepository

class AddProductToBucketUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(product: Product): Result<Cart> {
        return matuleRepository.addProductToBucket(product)
    }
}