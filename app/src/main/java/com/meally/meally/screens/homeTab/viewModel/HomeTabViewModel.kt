package com.meally.meally.screens.homeTab.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onFailure
import com.meally.domain.common.util.onSuccess
import com.meally.domain.diary.DiaryEntry
import com.meally.domain.diary.DiaryRepository
import com.meally.meally.screens.homeTab.homeTabMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeTabViewModel(
    private val diaryRepository: DiaryRepository,
): ViewModel() {

    private val selectedDate = MutableStateFlow(LocalDate.now())

    private val diaryEntries = MutableStateFlow(emptyList<DiaryEntry>())

    private val isLoading = MutableStateFlow(true)

    val state = combine(diaryEntries, selectedDate, isLoading) { diaryEntries, selectedDate, isLoading ->
        homeTabMapper(diaryEntries, selectedDate, isLoading)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = homeTabMapper(diaryEntries.value, selectedDate.value, isLoading.value)
    )

    fun refresh() {
        viewModelScope.launch {
            diaryRepository.getDiaryEntry(selectedDate.value).onSuccess { entries ->  diaryEntries.update { entries } }
            isLoading.update { false }
        }
    }

    fun selectDate(date: LocalDate) {
        isLoading.update { true }
        selectedDate.update { date }
        refresh()
    }
}