package com.meally.data.meals.di

import com.meally.data.meals.repository.MealRepositoryImpl
import com.meally.domain.meal.MealRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mealDataModule = module {
    singleOf(::MealRepositoryImpl) bind MealRepository::class
}