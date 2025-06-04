package com.meally.meally.screens.recentFood.ui.model

data class RecentFoodViewState(
    val isLoading: Boolean,
    val items: List<RecentFoodItem>,
)

data class RecentFoodItem(
    val name: String,
    val date: String,
    val barcode: String,
)
