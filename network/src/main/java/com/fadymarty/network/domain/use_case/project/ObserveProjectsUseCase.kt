package com.fadymarty.network.domain.use_case.project

import com.fadymarty.network.domain.model.Project
import com.fadymarty.network.domain.repository.MatuleRepository
import kotlinx.coroutines.flow.Flow

class ObserveProjectsUseCase(
    private val matuleRepository: MatuleRepository,
) {
    operator fun invoke(): Flow<List<Project>> {
        return matuleRepository.observeProjects()
    }
}