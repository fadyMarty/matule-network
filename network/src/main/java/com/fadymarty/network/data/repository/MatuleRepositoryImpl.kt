package com.fadymarty.network.data.repository

import com.fadymarty.network.common.safeCall
import com.fadymarty.network.data.remote.MatuleApi
import com.fadymarty.network.data.remote.dto.request.AuthRequest
import com.fadymarty.network.domain.manager.AuthManager
import com.fadymarty.network.domain.model.AuthResponse
import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.model.News
import com.fadymarty.network.domain.model.Order
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
            val authResponse = matuleApi.authWithPassword(
                request = AuthRequest(
                    identity = email,
                    password = password
                )
            ).toAuthResponse()

            authResponse.record.id?.let { id ->
                authManager.saveSession(
                    token = authResponse.token,
                    userId = id
                )
            }

            authResponse
        }
    }

    override suspend fun createUser(user: User): Result<User> {
        return safeCall {
            matuleApi.createUser(user.toUserDto()).toUser()
        }
    }

    override suspend fun updateUser(user: User): Result<User?> {
        return safeCall {
            user.id?.let { id ->
                matuleApi.updateUser(
                    id = id,
                    user = user.toUserDto()
                ).toUser()
            }
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

    override suspend fun searchProducts(query: String): Result<List<Product>> {
        return safeCall {
            matuleApi.searchProducts(
                filter = "(title ?~ '$query')"
            ).items.map { it.toProduct() }
        }
    }

    override suspend fun getProductById(id: String): Result<Product> {
        return safeCall {
            matuleApi.getProductById(id).toProduct()
        }
    }

    override suspend fun createCart(cart: Cart): Result<Cart> {
        return safeCall {
            matuleApi.createCart(cart.toCartDto()).toCart()
        }
    }

    override suspend fun updateCart(cart: Cart): Result<Cart?> {
        return safeCall {
            cart.id?.let { id ->
                matuleApi.updateCart(
                    id = cart.id,
                    cart = cart.toCartDto()
                ).toCart()
            }
        }
    }

    override suspend fun createOrder(order: Order): Result<Order> {
        return safeCall {
            matuleApi.createOrder(order.toOrderDto()).toOrder()
        }
    }

    override suspend fun getProjects(): Result<List<Project>> {
        val userId = authManager.getUserId().first()

        return safeCall {
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

    override suspend fun getCurrentUser(): Result<User?> {
        val userId = authManager.getUserId().first()

        return safeCall {
            userId?.let { matuleApi.getUserById(it).toUser() }
        }
    }

    override suspend fun getBucket(): Result<List<Cart>> {
        return safeCall {
            matuleApi.getBucket().items.map { it.toCart() }
        }
    }

    override suspend fun deleteCart(id: String): Result<Unit> {
        return safeCall {
            matuleApi.deleteCart(id)
        }
    }
}