package com.meally.meally.screens.userGraph.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onSuccess
import com.meally.domain.diary.DiaryRepository
import com.meally.domain.weight.WeightRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.userGraph.mapper.userGraphMapper
import com.meally.meally.screens.userGraph.state.UserGraphState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class UserGraphViewModel(
    private val weightRepository: WeightRepository,
    private val diaryRepository: DiaryRepository,
    private val navigator: Navigator,
) : ViewModel() {

    private val startDate = MutableStateFlow(LocalDate.now().minusDays(7))
    private val endDate = MutableStateFlow(LocalDate.now())

    private val state = MutableStateFlow(UserGraphState.Initial)

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    val viewState = combine(state, startDate, endDate) { state, startDate, endDate ->
        userGraphMapper(state, startDate, endDate)
    }
    .onStart {
        loadData(startDate.value, endDate.value)
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = userGraphMapper(state.value, startDate.value, endDate.value),
    )

    private suspend fun loadData(from: LocalDate, to: LocalDate) {
        withContext(Dispatchers.Default) {
            launch {
                weightRepository.getWeight(from, to).onSuccess { weightList ->
                    state.update { it.copy(
                        weights = weightList
                    ) }
                }
            }

            launch {
                diaryRepository.getDiarySummary(from, to).onSuccess { summaries ->
                    state.update { it.copy(
                       diarySummaries = summaries,
                    ) }
                }
            }

        }

    }

    fun dateRangeChanged(start: LocalDate, end: LocalDate) {
        viewModelScope.launch(Dispatchers.Default) {
            loadData(start, end)
            startDate.value = start
            endDate.value = end
        }
    }

    fun goBack() {
        navigator.goBack()
    }

}