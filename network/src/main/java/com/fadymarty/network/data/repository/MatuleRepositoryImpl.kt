package com.fadymarty.network.data.repository

import com.fadymarty.network.common.util.safeCall
import com.fadymarty.network.data.mappers.toAuthResponse
import com.fadymarty.network.data.mappers.toCart
import com.fadymarty.network.data.mappers.toCartDto
import com.fadymarty.network.data.mappers.toNews
import com.fadymarty.network.data.mappers.toOrder
import com.fadymarty.network.data.mappers.toOrderDto
import com.fadymarty.network.data.mappers.toProduct
import com.fadymarty.network.data.mappers.toProject
import com.fadymarty.network.data.mappers.toUser
import com.fadymarty.network.data.mappers.toUserDto
import com.fadymarty.network.data.remote.MatuleApi
import com.fadymarty.network.data.remote.dto.CartDto
import com.fadymarty.network.data.remote.dto.requests.AuthRequest
import com.fadymarty.network.domain.manager.AuthManager
import com.fadymarty.network.domain.model.AuthResponse
import com.fadymarty.network.domain.model.Cart
import com.fadymarty.network.domain.model.News
import com.fadymarty.network.domain.model.Order
import com.fadymarty.network.domain.model.Product
import com.fadymarty.network.domain.model.Project
import com.fadymarty.network.domain.model.User
import com.fadymarty.network.domain.repository.MatuleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class MatuleRepositoryImpl(
    private val matuleApi: MatuleApi,
    private val authManager: AuthManager,
) : MatuleRepository {

    private val _carts = MutableStateFlow<List<Cart>>(emptyList())
    private val _projects = MutableStateFlow<List<Project>>(emptyList())

    override fun observeCarts(): Flow<List<Cart>> = _carts

    override fun observeProjects(): Flow<List<Project>> = _projects

    override suspend fun login(email: String, password: String): Result<AuthResponse> {
        return safeCall {
            matuleApi
                .login(
                    AuthRequest(
                        identity = email,
                        password = password
                    )
                )
                .toAuthResponse()
                .also {
                    authManager.saveSession(
                        token = it.token,
                        userId = it.record.id!!
                    )
                }
        }
    }

    override suspend fun register(user: User): Result<AuthResponse> {
        return safeCall {
            matuleApi.register(user.toUserDto())
            matuleApi
                .login(
                    AuthRequest(
                        identity = user.email!!,
                        password = user.password!!
                    )
                )
                .toAuthResponse()
                .also {
                    authManager.saveSession(
                        token = it.token,
                        userId = it.record.id!!
                    )
                }
        }
    }

    override suspend fun updateUser(user: User): Result<User> {
        return safeCall {
            matuleApi
                .updateUser(
                    id = user.id!!,
                    user = user.toUserDto()
                )
                .toUser()
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

    override suspend fun addProductToCart(product: Product): Result<Cart> {
        return safeCall {
            val userId = authManager.getUserId().first()!!

            val cart = CartDto(
                userId = userId,
                productId = product.id,
                count = 1
            )

            matuleApi.createCart(cart)
                .toCart()
                .also { getCarts() }
        }
    }

    override suspend fun updateCart(cart: Cart): Result<Cart> {
        return safeCall {
            matuleApi
                .updateCart(
                    id = cart.id!!,
                    cart = cart.toCartDto()
                )
                .toCart()
                .also { getCarts() }
        }
    }

    override suspend fun createOrder(cart: Cart): Result<Order> {
        return safeCall {
            matuleApi.createOrder(cart.toOrderDto())
                .toOrder()
                .also { deleteCart(cart.id!!) }
        }
    }

    override suspend fun getProjects(): Result<List<Project>> {
        return safeCall {
            matuleApi.getProjects().items
                .map { it.toProject() }
                .also { projects ->
                    _projects.update { projects }
                }
        }
    }

    override suspend fun createProject(
        project: Project,
        imageBytes: ByteArray
    ): Result<Project> {
        return safeCall {
            matuleApi
                .createProject(
                    title = project.title.toRequestBody(),
                    typeProject = project.typeProject.toRequestBody(),
                    dateStart = project.dateStart.toRequestBody(),
                    dateEnd = project.dateEnd.toRequestBody(),
                    gender = project.gender?.toRequestBody(),
                    descriptionSource = project.descriptionSource.toRequestBody(),
                    category = project.category.toRequestBody(),
                    image = MultipartBody.Part.createFormData(
                        name = "image",
                        filename = "image.jpg",
                        body = imageBytes.toRequestBody()
                    ),
                    userId = project.userId!!.toRequestBody()
                )
                .toProject()
                .also { getProjects() }
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

    override suspend fun getCarts(): Result<List<Cart>> {
        return safeCall {
            matuleApi.getCarts().items
                .map { it.toCart() }
                .also { carts ->
                    _carts.update { carts }
                }
        }
    }

    override suspend fun deleteCart(id: String): Result<Unit> {
        return safeCall {
            matuleApi.deleteCart(id)
            getCarts()
        }
    }

    override suspend fun getProjectById(id: String): Result<Project> {
        return safeCall {
            matuleApi.getProjectById(id).toProject()
        }
    }

    override suspend fun getUsers(): Result<List<User>> {
        return safeCall {
            matuleApi.getUsers().items.map { it.toUser() }
        }
    }
}