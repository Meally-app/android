package com.meally.meally.ui.food

import com.meally.domain.food.Food

class FoodViewStateMapper {
    fun mapFoodItem(food: Food) =
        FoodItemViewState(
            id = food.id,
            name = food.name,
        )
}
