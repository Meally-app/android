package com.meally.meally.screens.homeTab.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onFailure
import com.meally.domain.common.util.onSuccess
import com.meally.domain.diary.DiaryEntry
import com.meally.domain.diary.DiaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeTabViewModel(
    private val diaryRepository: DiaryRepository,
): ViewModel() {

    private val _state = MutableStateFlow<List<DiaryEntry>>(emptyList())
    val state = _state.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            diaryRepository.getDiaryEntry(LocalDate.now()).onSuccess { _state.value = it }
        }
    }
}