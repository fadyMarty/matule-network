package com.fadymarty.network.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.fadymarty.network.data.manager.AuthManagerImpl
import com.fadymarty.network.data.remote.MatuleApi
import com.fadymarty.network.data.remote.MatuleAuthenticator
import com.fadymarty.network.data.remote.MatuleInterceptor
import com.fadymarty.network.domain.manager.AuthManager
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
            .authenticator(get())
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
}