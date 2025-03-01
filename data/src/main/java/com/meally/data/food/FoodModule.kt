package com.meally.data.food

import com.meally.domain.food.FoodRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val foodModule = module { singleOf(::FoodRepositoryImpl) bind FoodRepository::class }
