package com.meally.domain.user.di

import com.meally.domain.user.repository.UserRepository
import com.meally.domain.user.usecase.IsUserLoggedIn
import org.koin.dsl.module

val domainUserModule =
    module {
        single<IsUserLoggedIn> {
            IsUserLoggedIn {
                get<UserRepository>().me.value != null
            }
        }
    }
