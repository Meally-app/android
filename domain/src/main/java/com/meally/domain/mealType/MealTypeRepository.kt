package com.meally.domain.mealType

import com.meally.domain.common.util.Resource

interface MealTypeRepository {

    suspend fun getMealTypes(): Resource<List<MealType>>
}