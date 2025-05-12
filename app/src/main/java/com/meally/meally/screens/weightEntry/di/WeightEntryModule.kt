package com.meally.meally.screens.weightEntry.di

import com.meally.meally.screens.weightEntry.viewModel.WeightEntryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weightEntryModule = module {
    viewModelOf(::WeightEntryViewModel)
}