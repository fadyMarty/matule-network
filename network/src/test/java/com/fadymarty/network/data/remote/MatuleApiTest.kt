package com.fadymarty.network.data.remote

import com.fadymarty.network.data.remote.dto.CartDto
import com.fadymarty.network.data.remote.dto.OrderDto
import com.fadymarty.network.data.remote.dto.ProjectDto
import com.fadymarty.network.data.remote.dto.UserDto
import com.fadymarty.network.data.remote.dto.request.AuthRequest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MatuleApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var matuleApi: MatuleApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val json = Json { ignoreUnknownKeys = true }

        matuleApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MatuleApi::class.java)
    }

    @Test
    fun `authWithPassword should return AuthResponse with token`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "record": {
                        "collectionId": "collectionId",
                        "collectionName": "collectionName",
                        "created": "created",
                        "emailVisibility": true,
                        "firstname": "firstname",
                        "id": "id",
                        "lastname": "lastname",
                        "secondname": "secondname",
                        "updated": "updated",
                        "verified": true,
                        "dateBirthday": "datebirthday",
                        "gender": "gender",
                        "email": "email"
                    },
                    "token": "token"
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val authResponse = matuleApi.authWithPassword(
            request = AuthRequest(
                identity = "identity",
                password = "password"
            )
        )
        assertThat(authResponse.token).isEqualTo("token")
    }

    @Test
    fun `createUser should create and return user`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "collectionId": "collectionId",
                    "collectionName": "collectionName",
                    "created": "created",
                    "emailVisibility": true,
                    "firstname": "firstname",
                    "id": "id",
                    "lastname": "lastname",
                    "secondname": "secondname",
                    "updated": "updated",
                    "verified": true,
                    "dateBirthday": "datebirthday",
                    "gender": "gender",
                    "email": "email"
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val user = matuleApi.createUser(
            user = UserDto(
                firstName = "firstName",
                lastName = "lastName",
                secondName = "secondName",
                dateBirthday = "dateBirthday",
                gender = "gender",
                email = "email"
            )
        )
        assertThat(user.email).isEqualTo("email")
    }

    @Test
    fun `getNews should return list of news`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "page": 0,
                    "perPage": 0,
                    "totalPages": 0,
                    "totalItems": 0,
                    "items": [
                        {
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "id": "id",
                            "newsImage": "newsImage",
                            "created": "created",
                            "updated": "updated"
                        }
                    ]
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val news = matuleApi.getNews()
        assertThat(news.items).isNotEmpty()
    }

    @Test
    fun `getProducts should return list of products`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "page": 0,
                    "perPage": 0,
                    "totalPages": 0,
                    "totalItems": 0,
                    "items": [
                        {
                            "id": "id",
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "created": "created",
                            "updated": "updated",
                            "title": "title",
                            "description": "description",
                            "price": 0,
                            "typeCloses": "typeCloses",
                            "type": "string",
                            "approximateCost": "approximateCost"
                        }
                    ]
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val products = matuleApi.getProducts()
        assertThat(products.items).isNotEmpty()
    }

    @Test
    fun `searchProducts should return list of products`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "page": 0,
                    "perPage": 0,
                    "totalPages": 0,
                    "totalItems": 0,
                    "items": [
                        {
                            "id": "id",
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "created": "created",
                            "updated": "updated",
                            "title": "title",
                            "description": "description",
                            "price": 0,
                            "typeCloses": "typeCloses",
                            "type": "string",
                            "approximateCost": "approximateCost"
                        }
                    ]
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val products = matuleApi.searchProducts(filter = "(title ?~ 'title')")
        assertThat(products.items.any { it.title.contains("title") }).isTrue()
    }

    @Test
    fun `getProductById should return list of products`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "id": "id",
                    "collectionId": "collectionId",
                    "collectionName": "collectionName",
                    "created": "created",
                    "updated": "updated",
                    "title": "title",
                    "description": "description",
                    "price": 0,
                    "typeCloses": "typeCloses",
                    "type": "string",
                    "approximateCost": "approximateCost"
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val product = matuleApi.getProductById("id")
        assertThat(product.id).isEqualTo("id")
    }

    @Test
    fun `createCart should create and return cart`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "id": "id",
                    "collectionId": "collectionId",
                    "collectionName": "collectionName",
                    "created": "created",
                    "updated": "updated",
                    "user_id": "user_id",
                    "product_id": "product_id",
                    "count": 0
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val cart = matuleApi.createCart(
            cart = CartDto(
                userId = "userId",
                productId = "productId",
                count = 0
            )
        )
        assertThat(cart.id).isEqualTo("id")
    }

    @Test
    fun `updateCart should update and return cart`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "id": "id",
                    "collectionId": "collectionId",
                    "collectionName": "collectionName",
                    "created": "created",
                    "updated": "updated",
                    "user_id": "user_id",
                    "product_id": "product_id",
                    "count": 0
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val cart = matuleApi.updateCart(
            id = "id",
            cart = CartDto(
                userId = "userId",
                productId = "productId",
                count = 0
            )
        )
        assertThat(cart.id).isEqualTo("id")
    }

    @Test
    fun `createOrder should create and return order`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "id": "id",
                    "collectionId": "collectionId",
                    "collectionName": "collectionName",
                    "created": "created",
                    "updated": "updated",
                    "user_id": "user_id",
                    "product_id": "product_id",
                    "count": 0
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val order = matuleApi.createOrder(
            order = OrderDto(
                userId = "userId",
                productId = "productId",
                count = 0
            )
        )
        assertThat(order.id).isEqualTo("id")
    }

    @Test
    fun `getProjects should return list of projects`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "page": 0,
                    "perPage": 0,
                    "totalPages": 0,
                    "totalItems": 0,
                    "items": [
                        {
                            "id": "id",
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "created": "created",
                            "updated": "updated",
                            "title": "title",
                            "dateStart": "dateStart",
                            "dateEnd": "dateEnd",
                            "gender": "gender",
                            "description_source": "description_source",
                            "category": "category",
                            "image": "image",
                            "user_id": "user_id"
                        }
                    ]
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val projects = matuleApi.getProjects(
            filter = "(user_id = 'user_id')"
        )
        assertThat(projects.items.any { it.userId == "user_id" }).isTrue()
    }

    @Test
    fun `createProject should create and return project`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "id": "id",
                    "collectionId": "collectionId",
                    "collectionName": "collectionName",
                    "created": "created",
                    "updated": "updated",
                    "title": "title",
                    "dateStart": "dateStart",
                    "dateEnd": "dateEnd",
                    "gender": "gender",
                    "description_source": "description_source",
                    "category": "category",
                    "image": "image",
                    "user_id": "user_id"
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val project = matuleApi.createProject(
            project = ProjectDto(
                title = "title",
                dateStart = "dateStart",
                dateEnd = "dateEnd",
                descriptionSource = "descriptionSource",
                userId = "userId",
                typeProject = "typeProject",
                gender = "gender",
                category = "category",
                image = "image"
            )
        )
        assertThat(project.id).isEqualTo("id")
    }

    @Test
    fun `getUserById should return user`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "collectionId": "collectionId",
                    "collectionName": "collectionName",
                    "created": "created",
                    "emailVisibility": true,
                    "firstname": "firstname",
                    "id": "id",
                    "lastname": "lastname",
                    "secondname": "secondname",
                    "updated": "updated",
                    "verified": true,
                    "dateBirthday": "datebirthday",
                    "gender": "gender",
                    "email": "email"
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val user = matuleApi.getUserById("id")
        assertThat(user.id).isEqualTo("id")
    }

}