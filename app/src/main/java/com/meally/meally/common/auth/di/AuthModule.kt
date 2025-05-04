package com.meally.meally.common.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.meally.data.remote.network.interceptors.authorizationToken.TokenProvider
import com.meally.domain.auth.repository.AuthRepository
import com.meally.meally.common.auth.di.authorizationToken.TokenProviderImpl
import com.meally.meally.common.auth.di.repository.AuthRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule =
    module {
        singleOf(::TokenProviderImpl) bind TokenProvider::class
        single<FirebaseAuth> { FirebaseAuth.getInstance() }
        singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    }
