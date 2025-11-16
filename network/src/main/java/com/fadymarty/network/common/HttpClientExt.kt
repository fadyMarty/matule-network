package com.fadymarty.network.common

import retrofit2.HttpException
import java.io.IOException

suspend inline fun <reified T> safeCall(
    execute: suspend () -> T,
): Result<T> {
    return try {
        val response = execute()
        Result.success(response)
    } catch (e: HttpException) {
        Result.failure(e)
    } catch (e: IOException) {
        Result.failure(e)
    }
}