package com.meally.data.mealType.di

import com.meally.data.mealType.repository.MealTypeRepositoryImpl
import com.meally.domain.mealType.MealTypeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mealTypeModule = module {
    singleOf(::MealTypeRepositoryImpl) bind MealTypeRepository::class
}