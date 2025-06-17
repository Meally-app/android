package com.meally.meally.screens.auth.profile.di

import com.meally.meally.screens.auth.profile.viewModel.UserProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val userProfileModule = module {
    viewModelOf(::UserProfileViewModel)
}