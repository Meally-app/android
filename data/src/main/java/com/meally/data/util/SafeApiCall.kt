package com.meally.data.util

import com.meally.domain.common.util.Resource

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> =
    try {
        val result = apiCall()
        Resource.Success(result)
    } catch (e: Exception) {
        Resource.Failure(e)
    }
