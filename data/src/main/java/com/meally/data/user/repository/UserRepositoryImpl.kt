package com.meally.data.user.repository

import com.meally.data.remote.network.api.MeallyAppApi
import com.meally.data.user.dto.toDomain
import com.meally.data.util.safeApiCall
import com.meally.domain.common.util.Resource
import com.meally.domain.common.util.map
import com.meally.domain.common.util.onFailure
import com.meally.domain.common.util.onSuccess
import com.meally.domain.user.User
import com.meally.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserRepositoryImpl(
    private val meallyAppApi: MeallyAppApi,
) : UserRepository {
    private val _me = MutableStateFlow<User?>(null)
    override val me: StateFlow<User?> = _me.asStateFlow()

    override suspend fun test() {
        safeApiCall {
            meallyAppApi.test()
        }.onSuccess {
            println("[TEST] success lets go $it")
        }.onFailure {
            println("[TEST] you are cooked $it")
        }
    }

    override suspend fun me(): Resource<User> =
        safeApiCall {
            meallyAppApi.me()
        }.map {
            it.toDomain()
        }.onSuccess { user ->
            _me.update { user }
        }

    override fun clearLoggedInUser() {
        _me.update { null }
    }
}
