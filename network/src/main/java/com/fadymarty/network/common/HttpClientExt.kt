package com.fadymarty.network.common

suspend inline fun <reified T> safeCall(
    execute: suspend () -> T,
): Result<T> {
    return try {
        val response = execute()
        Result.success(response)
    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(e)
    }
}