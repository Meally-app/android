package com.meally.meally.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.auth.repository.AuthRepository
import com.meally.domain.common.util.onSuccess
import com.meally.domain.user.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignupViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    val userFlow =
        userRepository.me
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null,
            )

    fun test() {
        viewModelScope.launch(Dispatchers.Default) {
            userRepository.test()
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.Default) {
            authRepository.logout()
        }
    }

    fun signupNewUser(
        email: String,
        password: String,
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            authRepository
                .signupEmailPassword(
                    email = email,
                    password = password,
                ).onSuccess {
                    userRepository.me()
                }
        }
    }

    fun loginUser(
        email: String,
        password: String,
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            authRepository
                .loginEmailPassword(
                    email = email,
                    password = password,
                ).onSuccess {
                    userRepository.me()
                }
        }
    }
}
