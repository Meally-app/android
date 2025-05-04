package com.meally.data.user.dto

import com.meally.domain.user.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val username: String,
)

fun UserDto.toDomain() =
    User(
        id = id,
        email = email,
        username = username,
    )
