package com.fadymarty.network.domain.use_case.project

import com.fadymarty.network.domain.model.Project
import com.fadymarty.network.domain.repository.MatuleRepository

class CreateProjectUseCase(
    private val matuleRepository: MatuleRepository,
) {
    suspend operator fun invoke(project: Project): Result<Project> {
        return matuleRepository.createProject(project)
    }
}