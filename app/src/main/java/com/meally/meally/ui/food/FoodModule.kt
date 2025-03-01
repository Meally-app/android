package com.meally.meally.ui.food

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val foodModule =
    module {
        viewModelOf(::FoodViewModel)
        singleOf(::FoodViewStateMapper)
    }
