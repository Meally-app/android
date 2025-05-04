package com.meally.domain.user.repository

import com.meally.domain.common.util.Resource
import com.meally.domain.user.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val me: StateFlow<User?>

    suspend fun me(): Resource<User>

    suspend fun test()

    fun clearLoggedInUser()
}
