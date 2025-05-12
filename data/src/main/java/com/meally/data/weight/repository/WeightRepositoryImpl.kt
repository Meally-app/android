package com.meally.data.weight.repository

import com.meally.data.remote.network.api.MeallyAppApi
import com.meally.data.util.safeApiCall
import com.meally.data.weight.dto.toDomain
import com.meally.data.weight.dto.toDto
import com.meally.domain.common.util.Resource
import com.meally.domain.common.util.map
import com.meally.domain.weight.Weight
import com.meally.domain.weight.WeightRepository
import java.time.LocalDate

class WeightRepositoryImpl(
    private val meallyAppApi: MeallyAppApi,
): WeightRepository {
    override suspend fun insertWeight(weight: Double, date: LocalDate): Resource<Unit> = safeApiCall {
        meallyAppApi.insertWeight(
            Weight(weight, date).toDto()
        )
    }

    override suspend fun getWeight(from: LocalDate, to: LocalDate): Resource<List<Weight>> = safeApiCall {
        meallyAppApi.getWeight(from, to)
    }.map {
        it.map { it.toDomain() }
    }

    override suspend fun getWeightForDate(date: LocalDate): Resource<Weight?> = getWeight(from = date, to = date)
        .map { it.firstOrNull { weight -> weight.date == date } }

}