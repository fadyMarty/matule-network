package com.fadymarty.network.data.mappers

import com.fadymarty.network.data.remote.dto.ProductDto
import com.fadymarty.network.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        collectionId = collectionId,
        collectionName = collectionName,
        created = created,
        updated = updated,
        title = title,
        description = description,
        price = price,
        typeCloses = typeCloses,
        type = type,
        approximateCost = approximateCost
    )
}