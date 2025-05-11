package com.meally.meally.screens.homeTab

import com.meally.domain.diary.DiaryEntry
import com.meally.meally.screens.homeTab.ui.model.HomeTabViewState
import java.time.LocalDate

fun homeTabMapper(diaryEntries: List<DiaryEntry>, selectedDate: LocalDate, isLoading: Boolean) = HomeTabViewState(
    isLoading = isLoading,
    diaryEntries = diaryEntries,
    selectedDate = selectedDate,
)