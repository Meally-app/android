package com.meally.meally.screens.auth.profile.ui.model

import com.meally.domain.user.User

data class UserProfileViewState(
    val user: User,
    val isLoading: Boolean,
)
