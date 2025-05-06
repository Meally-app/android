package com.meally.data.diary.di

import com.meally.data.diary.repository.DiaryRepositoryImpl
import com.meally.domain.diary.DiaryRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val diaryModule = module {
    singleOf(::DiaryRepositoryImpl) bind DiaryRepository::class
}