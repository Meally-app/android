package com.meally.data.food.di

import com.meally.data.food.repository.FoodRepositoryImpl
import com.meally.domain.food.FoodRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val foodModule = module { singleOf(::FoodRepositoryImpl) bind FoodRepository::class }
