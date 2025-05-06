package com.meally.domain.mealType

data class MealType(
    val name: String,
    val orderInDay: Int,
) {
    companion object {
        val Empty = MealType(
            name = "",
            orderInDay = 0,
        )
    }
}
