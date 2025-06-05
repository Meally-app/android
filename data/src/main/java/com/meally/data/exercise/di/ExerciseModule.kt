package com.meally.data.exercise.di

import com.meally.data.exercise.ExerciseRepositoryImpl
import com.meally.domain.exercise.ExerciseRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val exerciseModule = module {
    singleOf(::ExerciseRepositoryImpl) bind ExerciseRepository::class
}