package com.meally.meally.screens.searchFood.ui.model

data class SearchFoodViewState(
    val isLoading: Boolean,
    val food: List<SearchFoodItem>,
)

data class SearchFoodItem(
    val name: String,
    val barcode: String,
    val calories: String,
)