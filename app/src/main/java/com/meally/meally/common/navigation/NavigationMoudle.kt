package com.meally.meally.common.navigation

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val navigationModule =
    module {
        singleOf(::NavigatorImpl) bind Navigator::class
        singleOf(NavHostControllerProvider::Default) bind NavHostControllerProvider::class
    }
