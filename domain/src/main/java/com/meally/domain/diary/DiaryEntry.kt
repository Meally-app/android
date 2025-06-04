package com.meally.domain.diary

data class DiaryEntry(
    val food: List<FoodEntry>,
    val exercise: List<ExerciseEntry>,
    val goalCalories: Int,
)
