package com.fadymarty.network.data.remote

import com.fadymarty.network.data.remote.dto.CartDto
import com.fadymarty.network.data.remote.dto.OrderDto
import com.fadymarty.network.data.remote.dto.ProjectDto
import com.fadymarty.network.data.remote.dto.UserDto
import com.fadymarty.network.data.remote.dto.requests.AuthRequest
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
    fun `login should return auth response with token`() = runTest {
        val authRequest = AuthRequest(
            identity = "indentity",
            password = "password"
        )
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
                        "dateBirthday": "dateBirthday",
                        "gender": "gender",
                        "email": "email"
                    },
                    "token": "token"
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.login(authRequest)
        assertThat(response.token).isEqualTo("token")
    }

    @Test
    fun `register should create new user`() = runTest {
        val user = UserDto(
            firstName = "firstname",
            id = "id",
            lastName = "lastname",
            secondName = "secondname",
            dateBirthday = "dateBirthday",
            gender = "gender",
            email = "email",
            password = "password",
            passwordConfirm = "passwordConfirm"
        )
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
                    "dateBirthday": "dateBirthday",
                    "gender": "gender",
                    "email": "email"
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.register(user)
        assertThat(response.id).isEqualTo(user.id)
    }

    @Test
    fun `updateProfile should update user`() = runTest {
        val user = UserDto(
            firstName = "firstname",
            id = "id",
            lastName = "lastname",
            secondName = "secondname",
            dateBirthday = "dateBirthday",
            gender = "gender",
            email = "email"
        )
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
                    "dateBirthday": "dateBirthday",
                    "gender": "gender",
                    "email": "email"
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.updateProfile(
            userId = "id",
            user = user
        )
        assertThat(response.id).isEqualTo("id")
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
                            "id": "id1",
                            "newsImage": "newsImage1",
                            "created": "created1",
                            "updated": "updated1"
                        },
                        {
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "id": "id2",
                            "newsImage": "newsImage2",
                            "created": "created2",
                            "updated": "updated2"
                        }
                    ]
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.getNews()
        assertThat(response.items).isNotEmpty()
    }

    @Test
    fun `getCatalog should return list of products`() = runTest {
        val mockResponse = MockResponse(
            body = """
                {
                    "page": 0,
                    "perPage": 0,
                    "totalPages": 0,
                    "totalItems": 0,
                    "items": [
                        {
                            "id": "id1",
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "created": "created1",
                            "updated": "updated1",
                            "title": "title1",
                            "description": "description1",
                            "price": 0,
                            "typeCloses": "typeCloses1",
                            "type": "type1",
                            "approximate_cost": "approximateCost1"
                        },
                        {
                            "id": "id2",
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "created": "created2",
                            "updated": "updated2",
                            "title": "title2",
                            "description": "description2",
                            "price": 0,
                            "typeCloses": "typeCloses2",
                            "type": "type2",
                            "approximate_cost": "approximateCost2"
                        }
                    ]
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.getCatalog()
        assertThat(response.items).isNotEmpty()
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
                            "id": "id1",
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "created": "created1",
                            "updated": "updated1",
                            "title": "title1",
                            "description": "description1",
                            "price": 0,
                            "typeCloses": "typeCloses1",
                            "type": "type1",
                            "approximate_cost": "approximate_cost1"
                        },
                        {
                            "id": "id2",
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "created": "created2",
                            "updated": "updated2",
                            "title": "title2",
                            "description": "description2",
                            "price": 0,
                            "typeCloses": "typeCloses2",
                            "type": "type2",
                            "approximate_cost": "approximate_cost2"
                        }
                    ]
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.searchProducts("(title ~ 'title')")
        assertThat(response.items.any { it.title.contains("title") }).isTrue()
    }

    @Test
    fun `getProductById should return product`() = runTest {
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
                    "type": "type",
                    "approximate_cost": "approximate_cost"
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.getProductById("id")
        assertThat(response.id).isEqualTo("id")
    }

    @Test
    fun `createCart should create new cart`() = runTest {
        val cart = CartDto(
            userId = "userId",
            productId = "productId",
            count = 1
        )
        val mockResponse = MockResponse(
            body = """
                {
                    "id": "id",
                    "collectionId": "collectionId",
                    "collectionName": "collectionName",
                    "created": "created",
                    "updated": "updated",
                    "user_id": "user_id",
                    "porduct_id": "product_id",
                    "count": 0
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.createCart(cart)
        assertThat(response.id).isEqualTo("id")
    }

    @Test
    fun `updateCart should update cart`() = runTest {
        val cart = CartDto(
            userId = "user_id",
            productId = "product_id",
            count = 1
        )
        val mockResponse = MockResponse(
            body = """
                {
                    "id": "bucketId",
                    "collectionId": "collectionId",
                    "collectionName": "collectionName",
                    "created": "created",
                    "updated": "updated",
                    "user_id": "user_id",
                    "porduct_id": "product_id",
                    "count": 0
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.updateCart("bucketId", cart)
        assertThat(response.id).isEqualTo("bucketId")
    }

    @Test
    fun `createOrder should create new order`() = runTest {
        val order = OrderDto(
            userId = "userId",
            productId = "productId",
            count = 1
        )
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
        val response = matuleApi.createOrder(order)
        assertThat(response.id).isEqualTo("id")
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
                            "id": "id1",
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "created": "created1",
                            "updated": "updated1",
                            "title": "title1",
                            "dateStart": "dateStart1",
                            "dateEnd": "dateEnd1",
                            "gender": "gender1",
                            "description_source": "description_source1",
                            "category": "category1",
                            "image": "image1",
                            "user_id": "user_id1"
                        },
                        {
                            "id": "id2",
                            "collectionId": "collectionId",
                            "collectionName": "collectionName",
                            "created": "created2",
                            "updated": "updated2",
                            "title": "title2",
                            "dateStart": "dateStart2",
                            "dateEnd": "dateEnd2",
                            "gender": "gender2",
                            "description_source": "description_source2",
                            "category": "category2",
                            "image": "image2",
                            "user_id": "user_id2"
                        }
                    ]
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.getProjects()
        assertThat(response.items).isNotEmpty()
    }

    @Test
    fun `createProject should create new project`() = runTest {
        val project = ProjectDto(
            title = "title",
            dateStart = "dateStart",
            dateEnd = "dateEnd",
            gender = "gender",
            descriptionSource = "description_sourcce",
            category = "category",
            image = "image",
            userId = "userId"
        )
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
        val response = matuleApi.createProject(project)
        assertThat(response.id).isEqualTo("id")
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
                    "dateBirthday": "dateBirthday",
                    "gender": "gender",
                    "email": "email"
                }
            """.trimIndent()
        )
        mockWebServer.enqueue(mockResponse)
        val response = matuleApi.getUserById("id")
        assertThat(response.id).isEqualTo("id")
    }
}