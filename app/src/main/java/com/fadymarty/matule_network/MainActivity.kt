package com.fadymarty.matule_network

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.fadymarty.network.domain.use_case.user.LoginUseCase
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    val loginUseCase: LoginUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            loginUseCase("asdf@asdf.ru", "qwQW12!@ ")
        }
    }
}