package com.fadymarty.network.domain.repository

import com.fadymarty.network.domain.model.AuthResponse
import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.model.News
import com.fadymarty.network.domain.model.Product
import com.fadymarty.network.domain.model.Project
import com.fadymarty.network.domain.model.User

interface MatuleRepository {

    suspend fun authWithPassword(
        email: String,
        password: String,
    ): Result<AuthResponse>

    suspend fun register(user: User): Result<AuthResponse>

    suspend fun updateUser(user: User): Result<User>

    suspend fun getNews(): Result<List<News>>

    suspend fun getProducts(): Result<List<Product>>

    suspend fun searchProducts(query: String, typeCloses: String?): Result<List<Product>>

    suspend fun getProductById(id: String): Result<Product>

    suspend fun addProductToBucket(product: Product): Result<Cart>

    suspend fun updateCart(cart: Cart): Result<Cart>

    suspend fun createOrder(bucket: List<Cart>): Result<Unit>

    suspend fun getProjects(): Result<List<Project>>

    suspend fun createProject(project: Project): Result<Project>

    suspend fun getUserById(id: String): Result<User>

    suspend fun getCurrentUser(): Result<User>

    suspend fun getBucket(): Result<List<Cart>>

    suspend fun deleteCart(id: String): Result<Unit>
}