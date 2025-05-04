package com.meally.meally.screens.startup.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.startup.usecase.DoStartupActions
import com.meally.domain.user.usecase.IsUserLoggedIn
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.destinations.SignupScreenDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartupViewModel(
    private val doStartupActions: DoStartupActions,
    private val isUserLoggedIn: IsUserLoggedIn,
    private val navigator: Navigator,
) : ViewModel() {
    init {
        handleStartup()
    }

    private fun handleStartup() {
        viewModelScope.launch(Dispatchers.Default) {
            doStartupActions()
            if (isUserLoggedIn()) {
                navigator.goToHome()
            } else {
                navigator.clearStack()
                navigator.navigate(SignupScreenDestination)
            }
        }
    }
}
