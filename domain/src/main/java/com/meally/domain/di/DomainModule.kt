package com.meally.domain.di

import com.meally.domain.startup.di.domainStartupModule
import com.meally.domain.user.di.domainUserModule

val domainModule = domainStartupModule + domainUserModule
