package com.fadymarty.network.data.remote.dto

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
)
