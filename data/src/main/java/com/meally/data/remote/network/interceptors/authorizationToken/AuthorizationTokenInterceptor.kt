package com.meally.data.remote.network.interceptors.authorizationToken

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationTokenInterceptor(
    private val tokenProvider: TokenProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking { tokenProvider.getToken() }

        return if (token != null) {
            val authenticatedRequest =
                originalRequest
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            chain.proceed(authenticatedRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
}
