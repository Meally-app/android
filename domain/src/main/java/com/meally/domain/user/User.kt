package com.meally.domain.user

data class User(
    val id: String,
    val email: String,
    val username: String,
) {
    companion object {
        val Empty = User(
            id = "",
            email = "",
            username = "",
        )
    }
}
