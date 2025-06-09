package com.meally.meally.screens.mealDetails.di

import com.meally.meally.screens.mealDetails.viewModel.MealDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mealDetailsModule = module {
    viewModelOf(::MealDetailsViewModel)
}