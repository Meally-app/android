package com.meally.meally.screens.userGraph.di

import com.meally.meally.screens.userGraph.viewModel.UserGraphViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val userGraphModule = module {
    viewModelOf(::UserGraphViewModel)
}