package com.fadymarty.network.data.mappers

import com.fadymarty.network.data.remote.dto.NewsDto
import com.fadymarty.network.domain.model.News

fun NewsDto.toNews(): News {
    return News(
        collectionId = collectionId,
        collectionName = collectionName,
        id = id,
        newsImage = newsImage,
        created = created,
        updated = updated
    )
}