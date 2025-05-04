package com.meally.data.remote.network.interceptors.authorizationToken

interface TokenProvider {
    suspend fun getToken(): String?
}
