package com.meally.domain.user.usecase

@JvmInline
value class IsUserLoggedIn(
    val isUserLoggedIn: () -> Boolean,
) {
    operator fun invoke() = isUserLoggedIn()
}
