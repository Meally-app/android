package com.meally.meally.screens.homeTab.di

import com.meally.meally.screens.homeTab.viewModel.HomeTabViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeTabModule = module {
    viewModelOf(::HomeTabViewModel)
}