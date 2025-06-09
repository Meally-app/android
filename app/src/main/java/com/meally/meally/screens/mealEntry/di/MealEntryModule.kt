package com.meally.meally.screens.mealEntry.di

import com.meally.meally.screens.mealEntry.viewModel.MealEntryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mealEntryModule = module {
    viewModelOf(::MealEntryViewModel)
}