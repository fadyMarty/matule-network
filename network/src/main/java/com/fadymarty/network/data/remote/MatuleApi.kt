package com.fadymarty.network.data.remote

import com.fadymarty.network.data.remote.dto.AuthResponseDto
import com.fadymarty.network.data.remote.dto.CartDto
import com.fadymarty.network.data.remote.dto.NewsDto
import com.fadymarty.network.data.remote.dto.OrderDto
import com.fadymarty.network.data.remote.dto.PocketbaseResponse
import com.fadymarty.network.data.remote.dto.ProductDto
import com.fadymarty.network.data.remote.dto.ProjectDto
import com.fadymarty.network.data.remote.dto.UserDto
import com.fadymarty.network.data.remote.dto.requests.AuthRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
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
    suspend fun getNews(): PocketbaseResponse<NewsDto>

    @GET("collections/products/records")
    suspend fun getProducts(): PocketbaseResponse<ProductDto>

    @GET("collections/products/records")
    suspend fun searchProducts(
        @Query("filter") filter: String,
    ): PocketbaseResponse<ProductDto>

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
        @Path("id") id: String,
        @Body cart: CartDto,
    ): CartDto

    @POST("collections/orders/records")
    suspend fun createOrder(
        @Body order: OrderDto,
    ): OrderDto

    @GET("collections/project/records")
    suspend fun getProjects(): PocketbaseResponse<ProjectDto>

    @Multipart
    @POST("collections/project/records")
    suspend fun createProject(
        @Part("title") title: RequestBody,
        @Part("typeProject") typeProject: RequestBody,
        @Part("dateStart") dateStart: RequestBody,
        @Part("dateEnd") dateEnd: RequestBody,
        @Part("gender") gender: RequestBody? = null,
        @Part("description_source") descriptionSource: RequestBody,
        @Part("category") category: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("user_id") userId: RequestBody,
    ): ProjectDto

    @GET("collections/users/records/{id}")
    suspend fun getUserById(
        @Path("id") id: String,
    ): UserDto

    @GET("collections/cart/records")
    suspend fun getCarts(): PocketbaseResponse<CartDto>

    @DELETE("collections/cart/records/{id}")
    suspend fun deleteCart(
        @Path("id") id: String,
    )

    @GET("collections/project/records/{id}")
    suspend fun getProjectById(
        @Path("id") id: String,
    ): ProjectDto

    @GET("collections/users/records")
    suspend fun getUsers(): PocketbaseResponse<UserDto>

    @POST("collections/users/auth-refresh")
    suspend fun refresh(
        @Header("Authorization") token: String,
    ): AuthResponseDto

    companion object {
        const val BASE_URL = "http://77.239.125.32:8090/api/"
    }
}