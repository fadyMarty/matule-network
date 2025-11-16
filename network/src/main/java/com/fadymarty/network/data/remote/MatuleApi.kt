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
import ge.tbcbank.retrocache.Cache
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

// Сетевой клиент с запросами к серверу
interface MatuleApi {

    // Авторизация
    @POST("collections/users/auth-with-password")
    suspend fun authenticate(
        @Body request: AuthRequest,
    ): AuthResponseDto

    // Создание пользователя
    @POST("collections/users/records")
    suspend fun register(
        @Body user: UserDto,
    ): UserDto

    // Изменение профиля
    @PATCH("collections/users/records/{id_user}")
    suspend fun updateProfile(
        @Path("id_user") userId: String,
        @Body user: UserDto,
    ): UserDto

    // Получение акций
    @Cache
    @GET("collections/news/records")
    suspend fun getNews(): PocketbaseResponseDto<NewsDto>

    // Получение каталога
    @Cache
    @GET("collections/products/records")
    suspend fun getCatalog(): PocketbaseResponseDto<ProductDto>

    // Поиск
    @GET("collections/products/records")
    suspend fun searchProducts(
        @Query("filter") filter: String?,
    ): PocketbaseResponseDto<ProductDto>

    // Получение описания товара
    @GET("collections/products/records/{id_product}")
    suspend fun getProductById(
        @Path("id_product") id: String,
    ): ProductDto

    // Добавление в корзину
    @POST("collections/cart/records")
    suspend fun createCart(
        @Body cart: CartDto,
    ): CartDto

    // Изменение корзины
    @PATCH("collections/cart/records/{id_bucket}")
    suspend fun updateCart(
        @Path("id_bucket") bucketId: String,
        @Body cart: CartDto,
    ): CartDto

    // Оформление заказа
    @POST("collections/orders/records")
    suspend fun createOrder(
        @Body order: OrderDto,
    ): OrderDto

    // Список проектов
    @GET("/collections/project/records")
    suspend fun getProjects(): PocketbaseResponseDto<ProjectDto>

    // Создание проекта
    @POST("collections/project/records")
    suspend fun createProject(
        @Body project: ProjectDto,
    ): ProjectDto

    // Получение информации о профиле
    @GET("collections/users/records/{id_user}")
    suspend fun getUserById(
        @Path("id_user") id: String,
    ): UserDto

    // Обновление токена
    @Headers("Content-Type: application/json")
    @POST("api/collections/users/auth-refresh")
    suspend fun refresh(
        @Header("Authorization") token: String,
    ): AuthResponseDto

    // Получение корзины
    @Cache
    @GET("collections/cart/records")
    suspend fun getBucket(
        @Query("filter") filter: String,
    ): PocketbaseResponseDto<CartDto>

    // Удаление из корзины
    @DELETE("collections/cart/records/{id}")
    suspend fun deleteCart(
        @Path("id") id: String,
    )

    companion object {
        const val BASE_URL = "https://api.matule.ru/api/"
    }
}