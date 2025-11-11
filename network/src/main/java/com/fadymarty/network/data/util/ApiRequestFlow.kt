package com.fadymarty.network.data.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> apiRequestFlow(call: suspend () -> T): Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading)
        val data = call()
        emit(Resource.Success(data))
    } catch (e: Exception) {
        emit(Resource.Error(e.message))
    }
}.flowOn(Dispatchers.IO)