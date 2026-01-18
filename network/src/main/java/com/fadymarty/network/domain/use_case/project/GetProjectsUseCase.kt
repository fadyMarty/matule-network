package com.fadymarty.network.domain.use_case.project

import com.fadymarty.network.domain.model.Project
import com.fadymarty.network.domain.repository.MatuleRepository

class GetProjectsUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(): Result<List<Project>> {
        return matuleRepository.getProjects()
    }
}