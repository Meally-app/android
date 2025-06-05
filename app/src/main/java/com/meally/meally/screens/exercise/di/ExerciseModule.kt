package com.meally.meally.screens.exercise.di

import com.meally.meally.screens.exercise.viewModel.ExerciseViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val exerciseModule = module {
    viewModelOf(::ExerciseViewModel)
}