package com.meally.meally.screens.userGraph.state

import com.meally.domain.diary.DiarySummaryDay
import com.meally.domain.weight.Weight

data class UserGraphState(
    val weights: List<Weight>,
    val diarySummaries: List<DiarySummaryDay>,
) {
    companion object {
        val Initial = UserGraphState(
            weights = emptyList(),
            diarySummaries = emptyList(),
        )
    }
}
