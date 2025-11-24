package com.fadymarty.network.domain.use_case.bucket

import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.repository.MatuleRepository

class GetBucketUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(): Result<List<Cart>> {
        return matuleRepository.getBucket()
    }
}