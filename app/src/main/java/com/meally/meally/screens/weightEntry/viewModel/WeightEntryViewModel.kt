package com.meally.meally.screens.weightEntry.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onFailure
import com.meally.domain.common.util.onSuccess
import com.meally.domain.weight.WeightRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.navArgs
import com.meally.meally.screens.weightEntry.ui.WeightEntryScreenNavArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class WeightEntryViewModel(
    private val navigator: Navigator,
    private val weightRepository: WeightRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<WeightEntryScreenNavArgs>()

    private val weight = MutableStateFlow(0.0)

    private val selectedDate = MutableStateFlow(navArgs.date)

    val viewState = selectedDate.asStateFlow()

    fun weightChanged(newWeight: String) {
        val newWeightAmount = newWeight.toDoubleOrNull() ?: return
        weight.update { newWeightAmount }
    }

    fun dateSelected(date: LocalDate) {
        selectedDate.update { date }
    }

    fun confirm() {
        viewModelScope.launch {
            weightRepository.insertWeight(
                weight.value,
                selectedDate.value,
            ).onFailure {
                println("[TEST] fail $it")
            }.onSuccess {
                println("[TEST] success $it")
            }
            navigator.goBack()
        }
    }

    fun goBack() {
        navigator.goBack()
    }
}