package com.meally.data.remote.network.di

import com.meally.data.remote.network.api.MeallyAppApi
import com.meally.data.remote.network.interceptors.authorizationToken.AuthorizationTokenInterceptor
import com.meally.domain.common.config.Configuration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule =
    module {

        single<HttpLoggingInterceptor> {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        singleOf(::AuthorizationTokenInterceptor)

        single<OkHttpClient> {
            OkHttpClient
                .Builder()
                .addInterceptor(get<AuthorizationTokenInterceptor>())
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
        }

        single<Retrofit> {
            Retrofit
                .Builder()
                .baseUrl(get<Configuration>().meallyApiBaseUrl)
                .client(get<OkHttpClient>())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single<MeallyAppApi> {
            get<Retrofit>().create(MeallyAppApi::class.java)
        }
    }
