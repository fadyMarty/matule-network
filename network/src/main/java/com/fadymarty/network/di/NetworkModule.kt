package com.fadymarty.network.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.fadymarty.network.common.util.Constants
import com.fadymarty.network.data.manager.AuthManagerImpl
import com.fadymarty.network.data.remote.MatuleApi
import com.fadymarty.network.data.remote.MatuleAuthenticator
import com.fadymarty.network.data.remote.MatuleInterceptor
import com.fadymarty.network.data.repository.MatuleRepositoryImpl
import com.fadymarty.network.domain.manager.AuthManager
import com.fadymarty.network.domain.repository.MatuleRepository
import com.fadymarty.network.domain.use_case.cart.AddProductToCartUseCase
import com.fadymarty.network.domain.use_case.cart.DeleteCartUseCase
import com.fadymarty.network.domain.use_case.cart.GetCartsUseCase
import com.fadymarty.network.domain.use_case.cart.ObserveCartsUseCase
import com.fadymarty.network.domain.use_case.cart.UpdateCartUseCase
import com.fadymarty.network.domain.use_case.order.CreateOrderUseCase
import com.fadymarty.network.domain.use_case.project.CreateProjectUseCase
import com.fadymarty.network.domain.use_case.project.GetProjectByIdUseCase
import com.fadymarty.network.domain.use_case.project.GetProjectsUseCase
import com.fadymarty.network.domain.use_case.project.ObserveProjectsUseCase
import com.fadymarty.network.domain.use_case.shop.GetNewsUseCase
import com.fadymarty.network.domain.use_case.shop.GetProductsUseCase
import com.fadymarty.network.domain.use_case.shop.SearchProductsUseCase
import com.fadymarty.network.domain.use_case.user.ClearSessionUseCase
import com.fadymarty.network.domain.use_case.user.GetCurrentUserUseCase
import com.fadymarty.network.domain.use_case.user.GetPinUseCase
import com.fadymarty.network.domain.use_case.user.GetTokenUseCase
import com.fadymarty.network.domain.use_case.user.GetUserByIdUseCase
import com.fadymarty.network.domain.use_case.user.GetUserIdUseCase
import com.fadymarty.network.domain.use_case.user.GetUsersUseCase
import com.fadymarty.network.domain.use_case.user.LoginUseCase
import com.fadymarty.network.domain.use_case.user.RegisterUseCase
import com.fadymarty.network.domain.use_case.user.SavePinUseCase
import com.fadymarty.network.domain.use_case.user.UpdateUserUseCase
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val networkModule = module {

    singleOf(::AuthManagerImpl) { bind<AuthManager>() }
    singleOf(::MatuleRepositoryImpl) { bind<MatuleRepository>() }

    singleOf(::MatuleInterceptor)
    singleOf(::MatuleAuthenticator)

    single {
        PreferenceDataStoreFactory.create {
            androidContext().preferencesDataStoreFile(Constants.SETTINGS)
        }
    }

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

    factoryOf(::LoginUseCase)
    factoryOf(::RegisterUseCase)
    factoryOf(::UpdateUserUseCase)
    factoryOf(::GetUserByIdUseCase)
    factoryOf(::GetCurrentUserUseCase)
    factoryOf(::GetUsersUseCase)
    factoryOf(::SavePinUseCase)
    factoryOf(::GetPinUseCase)
    factoryOf(::GetTokenUseCase)
    factoryOf(::GetUserIdUseCase)
    factoryOf(::ClearSessionUseCase)

    factoryOf(::GetNewsUseCase)
    factoryOf(::GetProductsUseCase)
    factoryOf(::SearchProductsUseCase)

    factoryOf(::GetCartsUseCase)
    factoryOf(::AddProductToCartUseCase)
    factoryOf(::DeleteCartUseCase)
    factoryOf(::UpdateCartUseCase)
    factoryOf(::ObserveCartsUseCase)

    factoryOf(::CreateOrderUseCase)

    factoryOf(::GetProjectsUseCase)
    factoryOf(::CreateProjectUseCase)
    factoryOf(::GetProjectByIdUseCase)
    factoryOf(::ObserveProjectsUseCase)
}