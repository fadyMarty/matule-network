package com.fadymarty.network.domain.repository

import com.fadymarty.network.domain.model.AuthResponse
import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.model.News
import com.fadymarty.network.domain.model.Order
import com.fadymarty.network.domain.model.Product
import com.fadymarty.network.domain.model.Project
import com.fadymarty.network.domain.model.User
import kotlinx.coroutines.flow.Flow

interface MatuleRepository {
    fun observeCarts(): Flow<List<Cart>>
    fun observeProjects(): Flow<List<Project>>
    suspend fun login(email: String, password: String): Result<AuthResponse>
    suspend fun register(user: User): Result<AuthResponse>
    suspend fun updateUser(user: User): Result<User>
    suspend fun getNews(): Result<List<News>>
    suspend fun getProducts(): Result<List<Product>>
    suspend fun searchProducts(query: String): Result<List<Product>>
    suspend fun getProductById(id: String): Result<Product>
    suspend fun addProductToCart(product: Product): Result<Cart>
    suspend fun updateCart(cart: Cart): Result<Cart>
    suspend fun createOrder(cart: Cart): Result<Order>
    suspend fun getProjects(): Result<List<Project>>
    suspend fun createProject(project: Project, imageBytes: ByteArray): Result<Project>
    suspend fun getUserById(id: String): Result<User>
    suspend fun getCurrentUser(): Result<User>
    suspend fun getCarts(): Result<List<Cart>>
    suspend fun deleteCart(id: String): Result<Unit>
    suspend fun getProjectById(id: String): Result<Project>
    suspend fun getUsers(): Result<List<User>>
}