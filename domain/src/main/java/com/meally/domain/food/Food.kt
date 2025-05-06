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
    val unitOfMeasurement: UnitOfMeasurement,
) {
    enum class UnitOfMeasurement(val abbreviation: String,) {
        GRAMS("g"), MILLILITERS("ml"), PIECES("pc");

        companion object {
            fun safeValueOf(value: String) = when (value) {
                "MILLILITERS" -> MILLILITERS
                else -> GRAMS
            }
        }
    }

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
                unitOfMeasurement = UnitOfMeasurement.GRAMS,
            )
    }
}
