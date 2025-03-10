package com.meally.meally.screens.auth.signup

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val signupModule =
    module {
        viewModelOf(::SignupViewModel)
    }
