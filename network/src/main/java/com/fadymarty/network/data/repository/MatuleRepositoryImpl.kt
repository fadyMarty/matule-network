package com.fadymarty.network.data.repository

import com.fadymarty.network.common.safeCall
import com.fadymarty.network.data.remote.MatuleApi
import com.fadymarty.network.data.remote.dto.CartDto
import com.fadymarty.network.data.remote.dto.OrderDto
import com.fadymarty.network.data.remote.dto.request.AuthRequest
import com.fadymarty.network.domain.manager.AuthManager
import com.fadymarty.network.domain.model.AuthResponse
import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.model.News
import com.fadymarty.network.domain.model.Product
import com.fadymarty.network.domain.model.Project
import com.fadymarty.network.domain.model.User
import com.fadymarty.network.domain.repository.MatuleRepository
import kotlinx.coroutines.flow.first

class MatuleRepositoryImpl(
    private val matuleApi: MatuleApi,
    private val authManager: AuthManager,
) : MatuleRepository {

    override suspend fun authWithPassword(
        email: String,
        password: String,
    ): Result<AuthResponse> {
        return safeCall {
            val authResponse = matuleApi
                .authWithPassword(AuthRequest(email, password))
                .toAuthResponse()

            authManager.saveSession(authResponse.token, authResponse.record.id!!)

            authResponse
        }
    }

    override suspend fun register(user: User): Result<AuthResponse> {
        return safeCall {
            val user = matuleApi.createUser(user.toUserDto()).toUser()

            val authResponse = matuleApi
                .authWithPassword(AuthRequest(user.email, user.password!!))
                .toAuthResponse()

            authManager.saveSession(authResponse.token, authResponse.record.id!!)

            authResponse
        }
    }

    override suspend fun updateUser(user: User): Result<User> {
        return safeCall {
            matuleApi.updateUser(user.id!!, user.toUserDto()).toUser()
        }
    }

    override suspend fun getNews(): Result<List<News>> {
        return safeCall {
            matuleApi.getNews().items.map { it.toNews() }
        }
    }

    override suspend fun getProducts(): Result<List<Product>> {
        return safeCall {
            matuleApi.getProducts().items.map { it.toProduct() }
        }
    }

    override suspend fun searchProducts(
        query: String,
        typeCloses: String?,
    ): Result<List<Product>> {
        return safeCall {
            val filters = mutableListOf<String>()

            if (query.isNotEmpty()) filters.add("title ?~ '$query'")
            if (typeCloses != null) filters.add("typeCloses = '$typeCloses'")

            val filter = filters.joinToString(" && ")

            matuleApi.searchProducts(
                filter = filter.ifEmpty { null }
            ).items.map { it.toProduct() }
        }
    }

    override suspend fun getProductById(id: String): Result<Product> {
        return safeCall {
            matuleApi.getProductById(id).toProduct()
        }
    }

    override suspend fun addProductToBucket(product: Product): Result<Cart> {
        return safeCall {
            val userId = authManager.getUserId().first()!!

            matuleApi.createCart(
                cart = CartDto(
                    userId = userId,
                    productId = product.id,
                    count = 1
                )
            ).toCart()
        }
    }

    override suspend fun updateCart(cart: Cart): Result<Cart> {
        return safeCall {
            matuleApi.updateCart(cart.id!!, cart.toCartDto()).toCart()
        }
    }

    override suspend fun createOrder(bucket: List<Cart>): Result<Unit> {
        return safeCall {
            bucket.forEach { cart ->
                matuleApi.createOrder(
                    order = OrderDto(
                        userId = cart.userId,
                        productId = cart.productId,
                        count = cart.count
                    )
                ).toOrder()
            }
        }
    }

    override suspend fun getProjects(): Result<List<Project>> {
        return safeCall {
            val userId = authManager.getUserId().first()!!

            matuleApi.getProjects(
                filter = "(user_id = '$userId')"
            ).items.map { it.toProject() }
        }
    }

    override suspend fun createProject(project: Project): Result<Project> {
        return safeCall {
            matuleApi.createProject(project.toProjectDto()).toProject()
        }
    }

    override suspend fun getUserById(id: String): Result<User> {
        return safeCall {
            matuleApi.getUserById(id).toUser()
        }
    }

    override suspend fun getCurrentUser(): Result<User> {
        return safeCall {
            val userId = authManager.getUserId().first()!!

            matuleApi.getUserById(userId).toUser()
        }
    }

    override suspend fun getBucket(): Result<List<Cart>> {
        return safeCall {
            val userId = authManager.getUserId().first()!!

            matuleApi.getBucket(
                filter = "(user_id = '$userId')"
            ).items.map { it.toCart() }
        }
    }

    override suspend fun deleteCart(id: String): Result<Unit> {
        return safeCall {
            matuleApi.deleteCart(id)
        }
    }
}