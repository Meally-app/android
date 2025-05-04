package com.meally.meally.common.config

import com.meally.domain.common.config.Configuration
import com.meally.meally.configurationInstance
import org.koin.dsl.module

val configurationModule =
    module {
        single<Configuration> {
            configurationInstance
        }
    }
