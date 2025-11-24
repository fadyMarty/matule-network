package com.fadymarty.network.common

import retrofit2.HttpException
import java.io.IOException

suspend inline fun <reified T> safeCall(
    execute: suspend () -> T,
): Result<T> {
    try {
        val response = execute()
        return Result.success(response)
    } catch (e: HttpException) {
        return Result.failure(e)
    } catch (e: IOException) {
        return Result.failure(e)
    }
}