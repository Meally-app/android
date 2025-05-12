package com.meally.data.weight.dto

import com.meally.domain.weight.Weight
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class WeightDto(
    val weight: Double,
    val date: String,
)

fun Weight.toDto() = WeightDto(
    weight = weight,
    date = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
)

fun WeightDto.toDomain() = Weight(
    weight = weight,
    date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
)
