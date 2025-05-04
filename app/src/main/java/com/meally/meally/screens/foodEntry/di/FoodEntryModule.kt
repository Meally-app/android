package com.meally.meally.screens.foodEntry.di

import com.meally.meally.screens.foodEntry.viewModel.FoodEntryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val foodEntryModule =
    module {
        viewModelOf(::FoodEntryViewModel)
    }
