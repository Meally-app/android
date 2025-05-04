package com.meally.domain.common.util

sealed interface Resource<T> {
    data class Success<T>(
        val data: T,
    ) : Resource<T>

    data class Failure<T>(
        val error: Exception,
    ) : Resource<T>
}

inline fun <T, R> Resource<T>.map(mapper: (T) -> R): Resource<R> =
    when (val resource = this) {
        is Resource.Failure -> Resource.Failure(resource.error)
        is Resource.Success -> Resource.Success(mapper(resource.data))
    }

inline fun <T> Resource<T>.onSuccess(block: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) {
        block(this.data)
    }
    return this
}

inline fun <T> Resource<T>.onFailure(block: (Exception) -> Unit): Resource<T> {
    if (this is Resource.Failure) {
        block(this.error)
    }
    return this
}
