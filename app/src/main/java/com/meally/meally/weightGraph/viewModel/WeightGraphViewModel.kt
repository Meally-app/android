package com.meally.meally.weightGraph.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.util.onSuccess
import com.meally.domain.weight.Weight
import com.meally.domain.weight.WeightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class WeightGraphViewModel(
    private val weightRepository: WeightRepository,
    private val from: LocalDate,
    private val to: LocalDate,
) : ViewModel() {

    private val _weightData = MutableStateFlow(emptyList<Weight>())
    val weightData = _weightData.asStateFlow()

    private fun loadWeights() {
        viewModelScope.launch {
            weightRepository.getWeight(from, to).onSuccess { weights ->
                _weightData.update { weights }
            }
        }
    }
}