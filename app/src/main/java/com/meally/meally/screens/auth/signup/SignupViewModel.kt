package com.meally.meally.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.meally.domain.auth.User
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.destinations.HomeTabScreenDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel(
    private val auth: FirebaseAuth,
    private val navigator: Navigator,
) : ViewModel() {
    private val _userFlow: MutableStateFlow<User> = MutableStateFlow(User("", "", ""))
    val userFlow = _userFlow.asStateFlow()

    fun signupNewUser(
        email: String,
        password: String,
    ) {
        auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.let {
                        navigator.navigate(HomeTabScreenDestination)
                        viewModelScope.launch(Dispatchers.Default) {
                            _userFlow.emit(
                                User(
                                    id = it.uid,
                                    email = it.email ?: "",
                                    username = it.displayName ?: "",
                                ),
                            )
                        }
                    }
                }
            }
    }

    fun loginUser(
        email: String,
        password: String,
    ) {
        auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.let {
                        viewModelScope.launch(Dispatchers.Default) {
                            it.getIdToken(false).addOnSuccessListener {
                                println("[TEST] token: ${it.token}")
                            }

                            _userFlow.emit(
                                User(
                                    id = it.uid,
                                    email = it.email ?: "",
                                    username = it.displayName ?: "",
                                ),
                            )
                        }
                    }
                }
            }
    }
}
