package com.meally.meally.screens.recentFood.di

import com.meally.meally.screens.recentFood.viewModel.RecentFoodViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val recentFoodModule = module {
    viewModelOf(::RecentFoodViewModel)
}