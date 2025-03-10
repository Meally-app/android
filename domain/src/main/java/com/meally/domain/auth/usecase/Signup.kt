package com.meally.domain.auth.usecase

import com.meally.domain.auth.User

interface Signup {
    suspend fun invoke(
        email: String,
        password: String,
    ): Result<User>
}
