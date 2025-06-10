package com.meally.meally.screens.createMeal.di

import com.meally.meally.screens.createMeal.viewModel.CreateMealViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val createMealModule = module {
    viewModelOf(::CreateMealViewModel)
}