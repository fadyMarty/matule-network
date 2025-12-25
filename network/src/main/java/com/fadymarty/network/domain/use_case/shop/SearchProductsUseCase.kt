package com.fadymarty.network.domain.use_case.shop

import com.fadymarty.network.domain.model.Product
import com.fadymarty.network.domain.repository.MatuleRepository

class SearchProductsUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(query: String): Result<List<Product>> {
        return matuleRepository.searchProducts(query)
    }
}