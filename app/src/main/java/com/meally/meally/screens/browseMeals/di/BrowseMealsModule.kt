package com.meally.meally.screens.browseMeals.di

import com.meally.meally.screens.browseMeals.viewModel.BrowseMealsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val browseMealsModule = module {
    viewModelOf(::BrowseMealsViewModel)
}