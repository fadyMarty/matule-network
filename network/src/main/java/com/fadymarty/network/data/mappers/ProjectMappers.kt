package com.fadymarty.network.data.mappers

import com.fadymarty.network.data.remote.dto.ProjectDto
import com.fadymarty.network.domain.model.Project

fun ProjectDto.toProject(): Project {
    return Project(
        id = id,
        collectionId = collectionId,
        collectionName = collectionName,
        created = created,
        updated = updated,
        title = title,
        typeProject = typeProject,
        dateStart = dateStart,
        dateEnd = dateEnd,
        gender = gender,
        descriptionSource = descriptionSource,
        category = category,
        image = image,
        userId = userId
    )
}

fun Project.toProjectDto(): ProjectDto {
    return ProjectDto(
        id = id,
        collectionId = collectionId,
        collectionName = collectionName,
        created = created,
        updated = updated,
        title = title,
        typeProject = typeProject,
        dateStart = dateStart,
        dateEnd = dateEnd,
        gender = gender,
        descriptionSource = descriptionSource,
        category = category,
        image = image,
        userId = userId
    )
}