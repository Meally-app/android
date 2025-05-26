package com.meally.domain.diary

import java.time.LocalDate

data class DiarySummaryDay(
    val calories: Double,
    val date: LocalDate,
) {
    companion object {
        val Initial = DiarySummaryDay(
            calories = 0.0,
            date = LocalDate.MIN,
        )
    }
}
