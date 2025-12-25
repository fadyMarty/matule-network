package com.fadymarty.network.data.repository

import com.fadymarty.network.common.safeCall
import com.fadymarty.network.data.remote.MatuleApi
import com.fadymarty.network.data.remote.dto.CartDto
import com.fadymarty.network.data.remote.dto.OrderDto
import com.fadymarty.network.data.remote.dto.requests.AuthRequest
import com.fadymarty.network.domain.manager.AuthManager
import com.fadymarty.network.domain.model.AuthResponse
import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.model.News
import com.fadymarty.network.domain.model.Product
import com.fadymarty.network.domain.model.Project
import com.fadymarty.network.domain.model.User
import com.fadymarty.network.domain.repository.MatuleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

class MatuleRepositoryImpl(
    private val matuleApi: MatuleApi,
    private val authManager: AuthManager,
) : MatuleRepository {

    private val carts = MutableStateFlow<List<Cart>>(emptyList())
    private val projects = MutableStateFlow<List<Project>>(emptyList())

    override fun observeCarts(): Flow<List<Cart>> = carts

    override fun observeProjects(): Flow<List<Project>> = projects

    override suspend fun login(
        email: String,
        password: String,
    ): Result<AuthResponse> {
        return safeCall {
            val authResponse = matuleApi
                .login(AuthRequest(email, password))
                .toAuthResponse()
            authManager.saveSession(
                token = authResponse.token,
                userId = authResponse.record.id!!
            )
            authResponse
        }
    }

    override suspend fun register(user: User): Result<AuthResponse> {
        return safeCall {
            matuleApi.register(user.toUserDto())
            val authResponse = matuleApi
                .login(AuthRequest(user.email, user.password!!))
                .toAuthResponse()
            authManager.saveSession(
                token = authResponse.token,
                userId = authResponse.record.id!!
            )
            authResponse
        }
    }

    override suspend fun updateUser(
        user: User,
    ): Result<User> {
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

    override suspend fun searchProducts(query: String): Result<List<Product>> {
        return safeCall {
            matuleApi.searchProducts(
                filter = "(title ?~ '$query')"
            ).items.map { it.toProduct() }
        }
    }

    override suspend fun addProductToCart(product: Product): Result<Cart> {
        return safeCall {
            val userId = authManager.getUserId().first()
            val cart = matuleApi.createCart(
                cart = CartDto(
                    userId = userId!!,
                    productId = product.id,
                    count = 1
                )
            ).toCart()
            getCarts()
            cart
        }
    }

    override suspend fun updateCart(cart: Cart): Result<Cart> {
        return safeCall {
            val cart = matuleApi.updateCart(cart.id!!, cart.toCartDto()).toCart()
            getCarts()
            cart
        }
    }

    override suspend fun createOrder(bucket: List<Cart>): Result<Unit> {
        return safeCall {
            bucket.forEach { cart ->
                val order = OrderDto(
                    userId = cart.userId,
                    productId = cart.productId,
                    count = cart.count
                )
                matuleApi.createOrder(order).toOrder()
            }
        }
    }

    override suspend fun getProjects(): Result<List<Project>> {
        return safeCall {
            matuleApi.getProjects().items
                .map { it.toProject() }
                .also { newProjects ->
                    projects.update { newProjects }
                }
        }
    }

    override suspend fun createProject(project: Project): Result<Project> {
        return safeCall {
            val project = matuleApi.createProject(project.toProjectDto()).toProject()
            getProjects()
            project
        }
    }

    override suspend fun getCurrentUser(): Result<User> {
        return safeCall {
            val userId = authManager.getUserId().first()
            matuleApi.getUserById(userId!!).toUser()
        }
    }

    override suspend fun getCarts(): Result<List<Cart>> {
        return safeCall {
            val userId = authManager.getUserId().first()
            matuleApi.getCarts(
                filter = "(user_id = '$userId')"
            ).items
                .map { it.toCart() }
                .also { newCarts ->
                    carts.update { newCarts }
                }
        }
    }

    override suspend fun deleteCart(id: String): Result<Unit> {
        return safeCall {
            matuleApi.deleteCart(id)
            getCarts()
        }
    }
}