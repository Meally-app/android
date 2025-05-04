package com.meally.meally.screens.startup.di

import com.meally.meally.screens.startup.viewModel.StartupViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val startupModule =
    module {
        viewModelOf(::StartupViewModel)
    }
