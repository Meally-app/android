package com.meally.data.weight.di

import com.meally.data.weight.repository.WeightRepositoryImpl
import com.meally.domain.weight.WeightRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val weightModule = module {
    singleOf(::WeightRepositoryImpl) bind WeightRepository::class
}