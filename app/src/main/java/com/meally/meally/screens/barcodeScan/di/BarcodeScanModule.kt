package com.meally.meally.screens.barcodeScan.di

import com.meally.meally.screens.barcodeScan.viewModel.BarcodeScanViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val barcodeScanModule =
    module {
        viewModelOf(::BarcodeScanViewModel)
    }
