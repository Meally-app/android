package com.meally.meally.screens.foodEntryOptions.di

import com.meally.meally.screens.foodEntryOptions.viewModel.FoodEntryOptionsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val foodEntryOptionsModule =
    module {
        viewModelOf(::FoodEntryOptionsViewModel)
    }
