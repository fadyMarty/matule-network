package com.fadymarty.network.data.remote

import com.fadymarty.network.data.remote.dto.AuthResponseDto
import com.fadymarty.network.data.remote.dto.CartDto
import com.fadymarty.network.data.remote.dto.NewsDto
import com.fadymarty.network.data.remote.dto.OrderDto
import com.fadymarty.network.data.remote.dto.PocketbaseResponseDto
import com.fadymarty.network.data.remote.dto.ProductDto
import com.fadymarty.network.data.remote.dto.ProjectDto
import com.fadymarty.network.data.remote.dto.UserDto
import com.fadymarty.network.data.remote.dto.requests.AuthRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MatuleApi {

    @POST("collections/users/auth-with-password")
    suspend fun login(
        @Body request: AuthRequest,
    ): AuthResponseDto

    @POST("collections/users/records")
    suspend fun register(
        @Body user: UserDto,
    ): UserDto

    @PATCH("collections/users/records/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body user: UserDto,
    ): UserDto

    @GET("collections/news/records")
    suspend fun getNews(): PocketbaseResponseDto<NewsDto>

    @GET("collections/products/records")
    suspend fun getProducts(): PocketbaseResponseDto<ProductDto>

    @GET("collections/products/records")
    suspend fun searchProducts(
        @Query("filter") filter: String?,
    ): PocketbaseResponseDto<ProductDto>

    @GET("collections/products/records/{id}")
    suspend fun getProductById(
        @Path("id") id: String,
    ): ProductDto

    @POST("collections/cart/records")
    suspend fun createCart(
        @Body cart: CartDto,
    ): CartDto

    @PATCH("collections/cart/records/{id}")
    suspend fun updateCart(
        @Path("id") bucketId: String,
        @Body cart: CartDto,
    ): CartDto

    @POST("collections/orders/records")
    suspend fun createOrder(
        @Body order: OrderDto,
    ): OrderDto

    @GET("collections/project/records")
    suspend fun getProjects(): PocketbaseResponseDto<ProjectDto>

    @POST("collections/project/records")
    suspend fun createProject(
        @Body project: ProjectDto,
    ): ProjectDto

    @GET("collections/users/records/{id}")
    suspend fun getUserById(
        @Path("id") id: String,
    ): UserDto

    @POST("api/collections/users/auth-refresh")
    suspend fun refresh(
        @Header("Authorization") token: String,
    ): AuthResponseDto

    @GET("collections/cart/records")
    suspend fun getCarts(
        @Query("filter") filter: String,
    ): PocketbaseResponseDto<CartDto>

    @DELETE("collections/cart/records/{id}")
    suspend fun deleteCart(
        @Path("id") id: String,
    )

    companion object {
        const val BASE_URL = "http://77.239.125.32:8090/api/"
    }
}