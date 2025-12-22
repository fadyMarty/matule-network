package com.fadymarty.network.domain.model

import com.fadymarty.network.data.remote.dto.ProjectDto

data class Project(
    val id: String? = null,
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val updated: String? = null,
    val title: String,
    val dateStart: String,
    val dateEnd: String,
    val gender: String,
    val descriptionSource: String,
    val category: String,
    val image: String,
    val userId: String,
) {
    fun toProjectDto(): ProjectDto {
        return ProjectDto(
            id = id,
            collectionId = collectionId,
            collectionName = collectionName,
            created = created,
            updated = updated,
            title = title,
            dateStart = dateStart,
            dateEnd = dateEnd,
            gender = gender,
            descriptionSource = descriptionSource,
            category = category,
            image = image,
            userId = userId
        )
    }
}
