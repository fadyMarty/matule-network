package com.fadymarty.network.data.remote.dto

import com.fadymarty.network.domain.model.Project
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDto(
    val id: String? = null,
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val updated: String? = null,
    val title: String,
    val dateStart: String,
    val dateEnd: String,
    val gender: String,
    @SerialName("description_source")
    val descriptionSource: String,
    val category: String,
    val image: String,
    @SerialName("user_id")
    val userId: String,
) {
    fun toProject(): Project {
        return Project(
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
