package com.meally.domain.auth.repository

import com.meally.domain.common.util.Resource

interface AuthRepository {
    suspend fun logout()

    suspend fun loginEmailPassword(
        email: String,
        password: String,
    ): Resource<Unit>

    suspend fun signupEmailPassword(
        email: String,
        password: String,
    ): Resource<Unit>
}
