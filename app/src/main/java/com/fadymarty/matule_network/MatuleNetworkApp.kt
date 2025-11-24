package com.fadymarty.matule_network

import android.app.Application
import com.fadymarty.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MatuleNetworkApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MatuleNetworkApp)
            modules(networkModule)
        }
    }
}