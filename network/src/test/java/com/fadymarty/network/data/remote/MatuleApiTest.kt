package com.fadymarty.network.data.remote

import com.fadymarty.network.data.remote.dto.CartDto
import com.fadymarty.network.data.remote.dto.UserDto
import com.fadymarty.network.data.remote.dto.requests.AuthRequest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MatuleApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var matuleApi: MatuleApi

    @Before
    fun setUp() {
        val json = Json { ignoreUnknownKeys = true }

        mockWebServer = MockWebServer()
        mockWebServer.start()
        matuleApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MatuleApi::class.java)
    }

    @Test
    fun `login() should return AuthResponse with token`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "record": {
                            "collectionId": "string",
                            "collectionName": "string",
                            "created": "string",
                            "emailVisibility": true,
                            "firstname": "Test",
                            "id": "user_id_123",
                            "lastname": "Testov",
                            "secondname": "Testovich",
                            "updated": "string",
                            "verified": true,
                            "datebirthday": "",
                            "gender": "Мужской"
                        },
                        "token": "token_123"
                    }
                """.trimIndent()
            )
        )

        val authResponse = matuleApi.login(
            request = AuthRequest(
                identity = "testov@gmail.com",
                password = "qwQW12!@ "
            )
        )

        assertThat(authResponse.token).isEqualTo("token_123")
    }

    @Test
    fun `register() should create new user and return it`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "collectionId": "string",
                        "collectionName": "string",
                        "created": "string",
                        "emailVisibility": true,
                        "firstname": "Test",
                        "id": "user_id_123",
                        "lastname": "Testov",
                        "secondname": "Testovich",
                        "updated": "string",
                        "verified": true,
                        "datebirthday": "",
                        "gender": "Мужской"
                    }
                """.trimIndent()
            )
        )

        val user = matuleApi.register(
            user = UserDto(
                firstName = "Test",
                lastName = "Testov",
                secondName = "Testovich",
                dateBirthday = "",
                gender = "Мужской",
                email = "testov@gmail.com",
                password = "qwQW12!@ ",
                passwordConfirm = "qwQW12!@ "
            )
        )

        assertThat(user.id).isEqualTo("user_id_123")
    }

    @Test
    fun `getNews() should return list of news`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "page": 0,
                        "perPage": 0,
                        "totalPages": 0,
                        "totalItems": 0,
                        "items": [
                            {
                                "collectionId": "string",
                                "collectionName": "string",
                                "id": "string",
                                "newsImage": "string",
                                "created": "string",
                                "updated": "string"
                            }
                        ]
                    }
                """.trimIndent()
            )
        )

        val news = matuleApi.getNews()

        assertThat(news.items).isNotEmpty()
    }

    @Test
    fun `getProducts() should return list of products`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "page": 0,
                        "perPage": 0,
                        "totalPages": 0,
                        "totalItems": 0,
                        "items": [
                            {
                                "id": "string",
                                "collectionId": "string",
                                "collectionName": "string",
                                "created": "string",
                                "updated": "string",
                                "title": "string",
                                "description": "string",
                                "price": 0,
                                "typeCloses": "string",
                                "type": "string",
                                "approximateCost": "string"
                            }
                        ]
                    }
                """.trimIndent()
            )
        )

        val products = matuleApi.getProducts()

        assertThat(products.items).isNotEmpty()
    }

    @Test
    fun `searchProducts() should return list of products`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "page": 0,
                        "perPage": 0,
                        "totalPages": 0,
                        "totalItems": 0,
                        "items": [
                            {
                                "id": "string",
                                "collectionId": "string",
                                "collectionName": "string",
                                "created": "string",
                                "updated": "string",
                                "title": "string",
                                "description": "string",
                                "price": 0,
                                "typeCloses": "string",
                                "type": "string",
                                "approximateCost": "string"
                            }
                        ]
                    }
                """.trimIndent()
            )
        )

        val products = matuleApi.searchProducts(
            filter = "(title ?~ 'string')"
        )

        assertThat(products.items).isNotEmpty()
    }

    @Test
    fun `getProductById() should return product by id`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "id": "product_id_123",
                        "collectionId": "string",
                        "collectionName": "string",
                        "created": "string",
                        "updated": "string",
                        "title": "string",
                        "description": "string",
                        "price": 0,
                        "typeCloses": "string",
                        "type": "string",
                        "approximateCost": "string"
                    }
                """.trimIndent()
            )
        )

        val product = matuleApi.getProductById("product_id_123")

        assertThat(product.id).isEqualTo("product_id_123")
    }

    @Test
    fun `createCart() should create cart and return it`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "id": "cart_id_123",
                        "collectionId": "string",
                        "collectionName": "string",
                        "created": "string",
                        "updated": "string",
                        "user_id": "user_id_123",
                        "product_id": "product_id_123",
                        "count": 1
                    }
                """.trimIndent()
            )
        )

        val cart = matuleApi.createCart(
            cart = CartDto(
                productId = "product_id_123",
                userId = "user_id_123",
                count = 1
            )
        )

        assertThat(cart.id).isEqualTo("cart_id_123")
    }

    @Test
    fun `updateCart() should update cart and return it`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "id": "cart_id_123",
                        "collectionId": "string",
                        "collectionName": "string",
                        "created": "string",
                        "updated": "string",
                        "user_id": "user_id_123",
                        "product_id": "product_id_123",
                        "count": 2
                    }
                """.trimIndent()
            )
        )

        val cart = matuleApi.updateCart(
            id = "cart_id_123",
            cart = CartDto(
                productId = "product_id_123",
                userId = "user_id_123",
                count = 2
            )
        )

        assertThat(cart.count).isEqualTo(2)
    }

    @Test
    fun `createOrder() should create order and return it`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "id": "order_id_123",
                        "collectionId": "string",
                        "collectionName": "string",
                        "created": "string",
                        "updated": "string",
                        "user_id": "user_id_123",
                        "product_id": "product_id_123",
                        "count": 1
                    }
                """.trimIndent()
            )
        )

        val order = matuleApi.updateCart(
            id = "order_id_123",
            cart = CartDto(
                productId = "product_id_123",
                userId = "user_id_123",
                count = 1
            )
        )

        assertThat(order.id).isEqualTo("order_id_123")
    }

    @Test
    fun `getProjects() should return list of projects`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "page": 0,
                        "perPage": 0,
                        "totalPages": 0,
                        "totalItems": 0,
                        "items": [
                            {
                                "id": "project_id_123",
                                "collectionId": "string",
                                "collectionName": "string",
                                "created": "string",
                                "updated": "string",
                                "title": "Project 123",
                                "typeProject": "Type 123",
                                "dateStart": "string",
                                "dateEnd": "string",
                                "gender": "string",
                                "description_source": "example.com",
                                "category": "Categroy 123",
                                "image": "string",
                                "user_id": "user_id_123"
                            }
                        ]
                    }
                """.trimIndent()
            )
        )

        val projects = matuleApi.getProjects()

        assertThat(projects.items).isNotEmpty()
    }

    @Test
    fun `createProject() should create project and return it`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "id": "project_id_123",
                        "collectionId": "string",
                        "collectionName": "string",
                        "created": "string",
                        "updated": "string",
                        "title": "Project 123",
                        "typeProject": "Type 123",
                        "dateStart": "string",
                        "dateEnd": "string",
                        "gender": "string",
                        "description_source": "example.com",
                        "category": "Categroy 123",
                        "image": "string",
                        "user_id": "user_id_123"
                    }
                """.trimIndent()
            )
        )

        val project = matuleApi.createProject(
            title = "Project 123".toRequestBody(),
            typeProject = "Type 123".toRequestBody(),
            dateStart = "".toRequestBody(),
            dateEnd = "".toRequestBody(),
            descriptionSource = "example.com".toRequestBody(),
            category = "Categroy 123".toRequestBody(),
            image = MultipartBody.Part.createFormData(
                name = "image_123",
                filename = "image_123.jpg",
                body = "image".toRequestBody()
            ),
            userId = "user_id_123".toRequestBody()
        )

        assertThat(project.id).isEqualTo("project_id_123")
    }

    @Test
    fun `getUserByIdUseCase() should return user by id`() = runTest {
        mockWebServer.enqueue(
            response = MockResponse(
                body = """
                    {
                        "collectionId": "string",
                        "collectionName": "string",
                        "created": "string",
                        "emailVisibility": true,
                        "firstname": "Test",
                        "id": "user_id_123",
                        "lastname": "Testov",
                        "secondname": "Testovich",
                        "updated": "string",
                        "verified": true,
                        "datebirthday": "",
                        "gender": "Мужской"
                    }
                """.trimIndent()
            )
        )

        val user = matuleApi.getUserById("user_id_123")

        assertThat(user.id).isEqualTo("user_id_123")
    }
}