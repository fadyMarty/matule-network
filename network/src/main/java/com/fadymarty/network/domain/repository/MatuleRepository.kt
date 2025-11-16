package com.fadymarty.network.domain.repository

import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.model.News
import com.fadymarty.network.domain.model.Order
import com.fadymarty.network.domain.model.Product
import com.fadymarty.network.domain.model.Project
import com.fadymarty.network.domain.model.User

interface MatuleRepository {
    suspend fun authenticate(
        email: String,
        password: String,
    ): Result<Unit>

    suspend fun register(user: User): Result<Unit>
    suspend fun updateProfile(userId: String, user: User): Result<User>
    suspend fun getNews(): Result<List<News>>
    suspend fun searchProducts(query: String, type: String?): Result<List<Product>>
    suspend fun addProductToBucket(product: Product): Result<Cart?>
    suspend fun updateCart(bucketId: String, cart: Cart): Result<Cart>
    suspend fun createOrder(order: Order): Result<Order>
    suspend fun getProjects(): Result<List<Project>>
    suspend fun createProject(project: Project): Result<Project>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun getBucket(): Result<List<Cart>>
    suspend fun deleteCart(id: String): Result<Unit>
}