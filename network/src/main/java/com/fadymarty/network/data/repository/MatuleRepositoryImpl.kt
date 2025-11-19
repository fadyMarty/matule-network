package com.fadymarty.network.data.repository

import com.fadymarty.network.common.safeCall
import com.fadymarty.network.data.remote.MatuleApi
import com.fadymarty.network.data.remote.dto.CartDto
import com.fadymarty.network.data.remote.dto.requests.AuthRequest
import com.fadymarty.network.domain.manager.AuthManager
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
    override suspend fun authenticate(
        email: String,
        password: String,
    ): Result<Unit> {
        return safeCall {
            val authResponse = matuleApi.authenticate(AuthRequest(email, password))
            authResponse.record.id?.let { id ->
                authManager.saveSession(authResponse.token, id)
            }
        }
    }

    override suspend fun register(user: User): Result<Unit> {
        return safeCall {
            matuleApi.register(user.toUserDto())
            user.password?.let { password ->
                val request = AuthRequest(user.email, password)
                val authResponse = matuleApi.authenticate(request)
                authResponse.record.id?.let {
                    authManager.saveSession(authResponse.token, it)
                }
            }
        }
    }

    override suspend fun updateProfile(
        userId: String,
        user: User,
    ): Result<User> {
        return safeCall {
            matuleApi.updateProfile(userId, user.toUserDto()).toUser()
        }
    }

    override suspend fun getNews(): Result<List<News>> {
        return safeCall {
            matuleApi.getNews().items.map { it.toNews() }
        }
    }

    override suspend fun getCatalog(): Result<List<Product>> {
        return safeCall {
            matuleApi.getCatalog().items.map { it.toProduct() }
        }
    }

    override suspend fun searchProducts(
        query: String,
        type: String?,
    ): Result<List<Product>> {
        return safeCall {
            matuleApi.searchProducts(
                filter = when {
                    type != null && query.isNotEmpty() -> "(title ?~ '$query' && typeCloses = '$type')"
                    type == null && query.isNotEmpty() -> "(title ?~ '$query')"
                    type != null && query.isEmpty() -> "(typeCloses = '$type')"
                    else -> null
                }
            ).items.map { it.toProduct() }
        }
    }

    override suspend fun addProductToBucket(product: Product): Result<Cart?> {
        return safeCall {
            val userId = authManager.getUserId().first()

            userId?.let {
                matuleApi.createCart(
                    cart = CartDto(
                        userId = userId,
                        productId = product.id,
                        count = 1
                    )
                ).toCart()
            }
        }
    }

    override suspend fun updateCart(bucketId: String, cart: Cart): Result<Cart> {
        return safeCall {
            matuleApi.updateCart(bucketId, cart.toCartDto()).toCart()
        }
    }

    override suspend fun createOrder(bucket: List<Cart>): Result<Unit> {
        return safeCall {
            bucket.forEach { cart ->
                val order = Order(
                    userId = cart.userId,
                    productId = cart.productId,
                    count = cart.count
                )
                matuleApi.createOrder(order.toOrderDto()).toOrder()
            }
        }
    }

    override suspend fun getProjects(): Result<List<Project>> {
        return safeCall {
            matuleApi.getProjects().items.map { it.toProject() }
        }
    }

    override suspend fun createProject(project: Project): Result<Project> {
        return safeCall {
            matuleApi.createProject(project.toProjectDto()).toProject()
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return safeCall {
            val userId = authManager.getUserId().first()
            userId?.let {
                matuleApi.getUserById(userId).toUser()
            }
        }
    }

    override suspend fun getBucket(): Result<List<Cart>> {
        return safeCall {
            val userId = authManager.getUserId().first()

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