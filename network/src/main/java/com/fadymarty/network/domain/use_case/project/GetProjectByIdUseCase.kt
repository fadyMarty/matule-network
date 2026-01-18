package com.fadymarty.network.domain.use_case.project

import com.fadymarty.network.domain.model.Project
import com.fadymarty.network.domain.repository.MatuleRepository

class GetProjectByIdUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(id: String): Result<Project> {
        return matuleRepository.getProjectById(id)
    }
}