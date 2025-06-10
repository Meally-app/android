package com.meally.domain.meal

import com.meally.domain.user.User

data class BrowseMeal(
    val id: String,
    val name: String,
    val calories: Double,
    val user: User,
    val isLiked: Boolean,
)
