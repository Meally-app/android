package com.meally.meally.weightGraph.di

import com.meally.meally.weightGraph.viewModel.WeightGraphViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import java.time.LocalDate

val weightGraphModule = module {
    viewModel { (from: LocalDate, to: LocalDate) ->
        WeightGraphViewModel(
            weightRepository = get(),
            from = from,
            to = to,
        )
    }
}