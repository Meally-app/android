package com.meally.meally.screens.searchFood.di

import com.meally.meally.screens.searchFood.viewModel.SearchFoodViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val searchFoodModule = module {
    viewModelOf(::SearchFoodViewModel)
}