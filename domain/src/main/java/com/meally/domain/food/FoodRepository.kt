package com.meally.domain.food

import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    val foodFlow: Flow<Food>
}
