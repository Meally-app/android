package com.meally.meally.screens.exercise.mapper

import com.meally.domain.diary.ExerciseEntry
import com.meally.domain.exercise.ExerciseForDate
import com.meally.meally.R
import com.meally.meally.screens.exercise.ui.model.ExerciseEntryViewState
import com.meally.meally.screens.exercise.ui.model.ExerciseScreenViewState
import java.time.LocalDate

fun exerciseScreenViewStateMapper(isLoading: Boolean, exerciseForDate: ExerciseForDate?): ExerciseScreenViewState {
    if (exerciseForDate == null) {
        return ExerciseScreenViewState(
            isLoading = true,
            isAuthorized = false,
            date = LocalDate.now(),
            exercise = listOf(),
        )
    }
    return ExerciseScreenViewState(
        isLoading = isLoading,
        isAuthorized = exerciseForDate.isAuthorized,
        date = exerciseForDate.date,
        exercise = exerciseForDate.exercise.map { it.toViewState() },
    )
}

fun ExerciseEntry.toViewState() = ExerciseEntryViewState(
    iconResource = mapExerciseIcon(this),
    name = name,
    calories = "${calories.toInt()} kcal",
)

private fun mapExerciseIcon(entry: ExerciseEntry): Int = when (entry.activity.name) {
    "Run" -> R.drawable.ic_activity_run
    "Swim" -> R.drawable.ic_activity_swim
    else -> R.drawable.ic_fire
}