package com.meally.data.diary.dto

import com.meally.domain.diary.ExerciseEntry
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class ExerciseEntryDto(
    val name: String,
    val caloriesBurnt: Double,
    val timeSpent: Long,
    val activity: ExerciseActivityDto,
    val date: String,
)

fun ExerciseEntryDto.toDomain() = ExerciseEntry(
    name = name,
    calories = caloriesBurnt,
    timeSpentInSeconds = timeSpent,
    activity = activity.toDomain(),
    date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
)
