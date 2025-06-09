package com.meally.meally.screens.userMeals.di

import com.meally.meally.screens.userMeals.viewModel.UserMealsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val userMealsModule = module {
    viewModelOf(::UserMealsViewModel)
}