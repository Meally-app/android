package com.meally.meally.screens.userGraph.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.meally.domain.diary.DiarySummaryDay
import com.meally.domain.weight.Weight
import com.meally.meally.common.time.util.toEpochMillis
import com.meally.meally.screens.userGraph.state.UserGraphState
import com.meally.meally.screens.userGraph.ui.model.UserGraphViewState
import java.time.LocalDate
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun userGraphMapper(state: UserGraphState, startDate: LocalDate, endDate: LocalDate): UserGraphViewState {
    val dates = getDates(startDate, endDate)
    return UserGraphViewState(
        isLoading = state.weights.isEmpty() || state.diarySummaries.isEmpty(),
        firstYValues = fillWeights(state.weights, dates),
        firstValuesTitle = "Weight",
        secondsYValues = fillCalories(state.diarySummaries, dates),
        secondValuesTitle = "Calories",
        dates = dates.map { it.toEpochMillis().toDouble() },
        startDate = startDate,
        endDate = endDate,
    )
}


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
private fun getDates(startDate: LocalDate, endDate: LocalDate) = startDate.datesUntil(endDate.plusDays(1)).toList()

private fun fillCalories(calories: List<DiarySummaryDay>, dates: List<LocalDate>) =
    buildList {
        dates.forEach { date ->
            val day = calories.firstOrNull { it.date == date }
            if (day != null) {
                add(day.calories.roundToInt().toDouble())
            } else {
                add(0.0)
            }
        }
    }

private fun fillWeights(weights: List<Weight>, dates: List<LocalDate>) =
    buildList {
        dates.forEach { date ->
            val weight = weights.firstOrNull { it.date == date }
            if (weight != null) {
                add(weight.weight)
            } else {
                add(0.0)
            }
        }
    }






