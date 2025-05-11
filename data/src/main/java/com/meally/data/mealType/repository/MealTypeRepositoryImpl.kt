package com.meally.data.mealType.repository

import com.meally.data.mealType.dto.toDomain
import com.meally.data.remote.network.api.MeallyAppApi
import com.meally.data.util.safeApiCall
import com.meally.domain.common.util.Resource
import com.meally.domain.common.util.map
import com.meally.domain.mealType.MealType
import com.meally.domain.mealType.MealTypeRepository

class MealTypeRepositoryImpl(
    private val meallyAppApi: MeallyAppApi,
): MealTypeRepository {

    override suspend fun getMealTypes(): Resource<List<MealType>> = safeApiCall {
        meallyAppApi.getMealTypes()
    }.map {
        it.map { mealTypeDto ->
            mealTypeDto.toDomain()
        }
    }

}