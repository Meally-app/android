package com.meally.domain.startup.usecase

import com.meally.domain.user.repository.UserRepository

interface DoStartupActions {
    suspend operator fun invoke()

    class Default(
        private val userRepository: UserRepository,
    ) : DoStartupActions {
        override suspend operator fun invoke() {
            userRepository.me()
        }
    }
}
