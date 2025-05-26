package com.meally.data.diary.dto

import com.meally.domain.diary.DiarySummaryDay
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class DiarySummaryDayDto(
    val calories: Double,
    val date: String,
)

fun DiarySummaryDayDto.toDomain() = DiarySummaryDay(
    calories = calories,
    date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
)
