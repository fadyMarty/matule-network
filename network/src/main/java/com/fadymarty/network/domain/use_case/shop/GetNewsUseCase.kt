package com.fadymarty.network.domain.use_case.shop

import com.fadymarty.network.domain.model.News
import com.fadymarty.network.domain.repository.MatuleRepository

class GetNewsUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(): Result<List<News>> {
        return matuleRepository.getNews()
    }
}