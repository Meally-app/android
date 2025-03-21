package com.meally.meally.di

import com.meally.data.di.dataModule
import com.meally.domain.di.domainModule
import com.meally.meally.common.di.commonModule
import com.meally.meally.screens.di.screensModule

val appModule = domainModule + dataModule + screensModule + commonModule
