package com.fadymarty.network.common

suspend inline fun <reified T> safeCall(
    execute: suspend () -> T,
): Result<T> {
    try {
        val response = execute()
        return Result.success(response)
    } catch (e: Exception) {
        return Result.failure(e)
    }
}