package com.meally.meally.di

import android.app.Activity
import com.meally.data.di.dataModule
import com.meally.domain.di.domainModule
import com.meally.meally.common.di.commonModule
import com.meally.meally.screens.di.screensModule
import org.koin.dsl.module

val appModule = domainModule + dataModule + screensModule + commonModule + module {
    factory<Activity> { getProperty("activity") }
}
