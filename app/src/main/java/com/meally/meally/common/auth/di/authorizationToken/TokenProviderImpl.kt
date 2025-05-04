package com.meally.meally.common.auth.di.authorizationToken

import com.google.firebase.auth.FirebaseAuth
import com.meally.data.remote.network.interceptors.authorizationToken.TokenProvider
import kotlinx.coroutines.tasks.await

class TokenProviderImpl(
    private val firebaseAuth: FirebaseAuth,
) : TokenProvider {
    override suspend fun getToken(): String? {
        val currentUser = firebaseAuth.currentUser
        val token =
            runCatching {
                currentUser?.getIdToken(false)?.await()?.token
            }.getOrNull()

        return token
    }
}
