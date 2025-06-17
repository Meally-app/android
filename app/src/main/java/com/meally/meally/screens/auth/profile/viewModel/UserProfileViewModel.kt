package com.meally.meally.screens.auth.profile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.auth.repository.AuthRepository
import com.meally.domain.common.util.onSuccess
import com.meally.domain.user.User
import com.meally.domain.user.repository.UserRepository
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.auth.profile.ui.model.UserProfileViewState
import com.meally.meally.screens.destinations.SignupScreenDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val navigator: Navigator,
) : ViewModel() {

    private val user = MutableStateFlow(User.Empty)

    private val isLoading = MutableStateFlow(true)

    val viewState = combine(user, isLoading) { user, isLoading ->
        UserProfileViewState(
            user = user,
            isLoading = isLoading,
        )
    }
        .onStart { loadData() }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserProfileViewState(User.Empty, true),
    )

    fun logout() {
        viewModelScope.launch(Dispatchers.Default) {
            authRepository.logout()
            navigator.navigate(SignupScreenDestination)
        }
    }

    fun goBack() {
        navigator.goBack()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Default) {
            userRepository.me().onSuccess { loadedUser ->
                user.update { loadedUser }
                isLoading.update { false }
            }
        }
    }
}