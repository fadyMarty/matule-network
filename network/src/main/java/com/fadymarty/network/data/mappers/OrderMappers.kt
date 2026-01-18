package com.fadymarty.network.data.mappers

import com.fadymarty.network.data.remote.dto.OrderDto
import com.fadymarty.network.domain.model.Order

fun OrderDto.toOrder(): Order {
    return Order(
        id = id,
        collectionId = collectionId,
        collectionName = collectionName,
        created = created,
        updated = updated,
        userId = userId,
        productId = productId,
        count = count
    )
}

fun Order.toOrderDto(): OrderDto {
    return OrderDto(
        id = id,
        collectionId = collectionId,
        collectionName = collectionName,
        created = created,
        updated = updated,
        userId = userId,
        productId = productId,
        count = count
    )
}