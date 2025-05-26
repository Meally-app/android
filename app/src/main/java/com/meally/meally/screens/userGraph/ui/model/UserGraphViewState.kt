package com.meally.meally.screens.userGraph.ui.model

import java.time.LocalDate

data class UserGraphViewState(
    val isLoading: Boolean,
    val firstYValues: List<Double>,
    val firstValuesTitle: String,
    val secondsYValues: List<Double>?,
    val secondValuesTitle: String,
    val dates: List<Double>,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
