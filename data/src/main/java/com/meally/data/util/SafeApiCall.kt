package com.meally.data.util

import com.meally.domain.common.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> =
    try {
        withContext(Dispatchers.IO) {
            val result = apiCall()
            Resource.Success(result)
        }
    } catch (e: Exception) {
        println("[TEST] safeApiCall fail $e")
        Resource.Failure(e)
    }
