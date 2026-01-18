package com.fadymarty.network.domain.model

data class Project(
    val id: String? = null,
    val collectionId: String? = null,
    val collectionName: String? = null,
    val created: String? = null,
    val updated: String? = null,
    val title: String,
    val typeProject: String,
    val dateStart: String,
    val dateEnd: String,
    val gender: String? = null,
    val descriptionSource: String,
    val category: String,
    val image: String? = null,
    val userId: String? = null,
)