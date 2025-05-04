package com.meally.domain.startup.di

import com.meally.domain.startup.usecase.DoStartupActions
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainStartupModule =
    module {
        singleOf(DoStartupActions::Default) bind DoStartupActions::class
    }
