package com.meally.meally.screens.exercise.ui.model

import androidx.annotation.DrawableRes
import com.meally.domain.diary.ExerciseEntry
import java.time.LocalDate

data class ExerciseScreenViewState(
    val isLoading: Boolean,
    val isAuthorized: Boolean,
    val date: LocalDate,
    val exercise: List<ExerciseEntryViewState>,
)

data class ExerciseEntryViewState(
    @DrawableRes val iconResource: Int,
    val name: String,
    val calories: String,
)
