package com.meally.data.food

import com.meally.domain.food.Food
import com.meally.domain.food.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.withContext

class FoodRepositoryImpl : FoodRepository {
    private val _foodFlow = MutableSharedFlow<Food>(replay = 1)
    override val foodFlow: Flow<Food> = _foodFlow.onSubscription { fetchFood() }

    private suspend fun fetchFood() =
        withContext(Dispatchers.IO) {
            delay(1500L)
            _foodFlow.emit(Food(1L, "Sourdough bread"))
        }
}
