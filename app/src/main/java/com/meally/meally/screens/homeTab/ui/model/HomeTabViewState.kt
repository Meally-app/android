package com.meally.meally.screens.homeTab.ui.model

import com.meally.domain.diary.DiaryEntry
import java.time.LocalDate

data class HomeTabViewState(
    val isLoading: Boolean,
    val diaryEntries: List<DiaryEntry>,
    val selectedDate: LocalDate,
)