package com.meally.meally.screens.foodInfo.di

import com.meally.meally.screens.foodInfo.viewModel.FoodInfoViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val foodInfoModule =
    module {
        viewModelOf(::FoodInfoViewModel)
    }
