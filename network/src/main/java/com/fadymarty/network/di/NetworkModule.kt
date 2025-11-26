package com.fadymarty.network.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.fadymarty.network.data.manager.AuthManagerImpl
import com.fadymarty.network.data.remote.MatuleApi
import com.fadymarty.network.data.remote.MatuleAuthenticator
import com.fadymarty.network.data.remote.MatuleInterceptor
import com.fadymarty.network.data.repository.MatuleRepositoryImpl
import com.fadymarty.network.domain.manager.AuthManager
import com.fadymarty.network.domain.repository.MatuleRepository
import com.fadymarty.network.domain.use_case.bucket.AddProductToBucketUseCase
import com.fadymarty.network.domain.use_case.bucket.DeleteCartUseCase
import com.fadymarty.network.domain.use_case.bucket.GetBucketUseCase
import com.fadymarty.network.domain.use_case.bucket.UpdateCartUseCase
import com.fadymarty.network.domain.use_case.order.CreateOrderUseCase
import com.fadymarty.network.domain.use_case.pin.GetPinUseCase
import com.fadymarty.network.domain.use_case.pin.SavePinUseCase
import com.fadymarty.network.domain.use_case.project.CreateProjectUseCase
import com.fadymarty.network.domain.use_case.project.GetProjectsUseCase
import com.fadymarty.network.domain.use_case.shop.GetNewsUseCase
import com.fadymarty.network.domain.use_case.shop.GetProductByIdUseCase
import com.fadymarty.network.domain.use_case.shop.GetProductsUseCase
import com.fadymarty.network.domain.use_case.shop.SearchProductsUseCase
import com.fadymarty.network.domain.use_case.user.AuthWithPasswordUseCase
import com.fadymarty.network.domain.use_case.user.ClearSessionUseCase
import com.fadymarty.network.domain.use_case.user.GetCurrentUserUseCase
import com.fadymarty.network.domain.use_case.user.GetUserByIdUseCase
import com.fadymarty.network.domain.use_case.user.RegisterUseCase
import com.fadymarty.network.domain.use_case.user.UpdateUserUseCase
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val Context.dataStore by preferencesDataStore("settings")

val networkModule = module {

    singleOf(::AuthManagerImpl) { bind<AuthManager>() }

    singleOf(::MatuleInterceptor)

    singleOf(::MatuleAuthenticator)

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(get<MatuleInterceptor>())
            .authenticator(get<MatuleAuthenticator>())
            .build()
    }

    single {
        val json = Json { ignoreUnknownKeys = true }

        Retrofit.Builder()
            .baseUrl(MatuleApi.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(get())
            .build()
            .create(MatuleApi::class.java)
    }

    singleOf(::MatuleRepositoryImpl) { bind<MatuleRepository>() }

    singleOf(::AuthWithPasswordUseCase)

    singleOf(::RegisterUseCase)

    singleOf(::SavePinUseCase)

    singleOf(::GetPinUseCase)

    singleOf(::ClearSessionUseCase)

    singleOf(::UpdateUserUseCase)

    singleOf(::GetNewsUseCase)

    singleOf(::GetProductsUseCase)

    singleOf(::SearchProductsUseCase)

    singleOf(::GetProductByIdUseCase)

    singleOf(::AddProductToBucketUseCase)

    singleOf(::UpdateCartUseCase)

    singleOf(::CreateOrderUseCase)

    singleOf(::GetProjectsUseCase)

    singleOf(::CreateProjectUseCase)

    singleOf(::GetUserByIdUseCase)

    singleOf(::GetCurrentUserUseCase)

    singleOf(::GetBucketUseCase)

    singleOf(::DeleteCartUseCase)

}