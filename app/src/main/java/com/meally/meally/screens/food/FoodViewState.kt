package com.meally.meally.screens.food

import kotlinx.serialization.Serializable

data class FoodViewState(
    val isLoading: Boolean,
    val barcode: String,
) {
    companion object {
        val Initial =
            FoodViewState(
                isLoading = false,
                barcode = "",
            )
    }
}

@Serializable
data class FoodItemViewState(
    val name: String,
    val imageUrl: String,
    val calories: String,
    val carbs: String,
    val protein: String,
    val fat: String,
) {
    companion object {
        val Initial =
            FoodItemViewState(
                name = "",
                imageUrl = "",
                calories = "",
                carbs = "",
                protein = "",
                fat = "",
            )
    }
}
