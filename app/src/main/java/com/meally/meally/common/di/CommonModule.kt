package com.meally.meally.common.di

import com.meally.meally.common.auth.di.authModule
import com.meally.meally.common.navigation.navigationModule

val commonModule =
    navigationModule +
        authModule
