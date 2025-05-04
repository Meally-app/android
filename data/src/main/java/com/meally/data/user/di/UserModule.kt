package com.meally.data.user.di

import com.meally.data.user.repository.UserRepositoryImpl
import com.meally.domain.user.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule =
    module {
        singleOf(::UserRepositoryImpl) bind UserRepository::class
    }
