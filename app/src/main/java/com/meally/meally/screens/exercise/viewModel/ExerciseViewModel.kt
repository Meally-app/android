package com.meally.meally.screens.exercise.viewModel

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.common.config.Configuration
import com.meally.domain.common.util.onSuccess
import com.meally.domain.exercise.ExerciseForDate
import com.meally.domain.exercise.ExerciseRepository
import com.meally.domain.thirdParty.ThirdPartyRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.exercise.mapper.exerciseScreenViewStateMapper
import com.meally.meally.screens.exercise.ui.ExerciseScreenNavArgs
import com.meally.meally.screens.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class ExerciseViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val navigator: Navigator,
    private val configuration: Configuration,
    private val thirdPartyRepository: ThirdPartyRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<ExerciseScreenNavArgs>()

    private val isLoading = MutableStateFlow(false)

    private val exerciseForDate = MutableStateFlow<ExerciseForDate?>(null)

    val viewState = combine(isLoading, exerciseForDate, ::exerciseScreenViewStateMapper)
        .onStart { loadData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = exerciseScreenViewStateMapper(isLoading.value, exerciseForDate.value),
        )

    fun goBack() {
        navigator.goBack()
    }

    fun authorize() {
        val intentUri = Uri.parse("https://www.strava.com/oauth/mobile/authorize")
            .buildUpon()
            .appendQueryParameter("client_id", configuration.stravaClientId)
            .appendQueryParameter("redirect_uri", configuration.stravaRedirectUrl)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("approval_prompt", "force")
            .appendQueryParameter("scope", "activity:read")
            .build()

        val intent = Intent(Intent.ACTION_VIEW, intentUri)
        navigator.launchIntent(intent)
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Default) {
            exerciseRepository.getExerciseForDate(navArgs.date)
                .onSuccess { exercise ->
                    exerciseForDate.update { exercise }
                }

            isLoading.update { false }
        }
    }
}