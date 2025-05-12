package com.meally.domain.weight

import com.meally.domain.common.util.Resource
import java.time.LocalDate

interface WeightRepository {
    suspend fun insertWeight(weight: Double, date: LocalDate): Resource<Unit>

    suspend fun getWeight(from: LocalDate, to: LocalDate): Resource<List<Weight>>

    suspend fun getWeightForDate(date: LocalDate): Resource<Weight?>
}