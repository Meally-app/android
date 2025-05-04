package com.meally.domain.food

import com.meally.domain.barcode.Barcode
import com.meally.domain.common.util.Resource

interface FoodRepository {
    suspend fun getFood(barcode: Barcode): Resource<Food>
}
