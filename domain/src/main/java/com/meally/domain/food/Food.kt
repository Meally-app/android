package com.meally.domain.food

data class Food(
    val id: String,
    val name: String,
    val barcode: String,
    val calories: Double,
    val fat: Double,
    val saturatedFat: Double?,
    val carbs: Double,
    val sugars: Double?,
    val protein: Double,
    val imageUrl: String,
) {
    companion object {
        val Empty =
            Food(
                id = "",
                name = "",
                barcode = "",
                calories = 0.0,
                fat = 0.0,
                saturatedFat = null,
                carbs = 0.0,
                sugars = null,
                protein = 0.0,
                imageUrl = "",
            )
    }
}
