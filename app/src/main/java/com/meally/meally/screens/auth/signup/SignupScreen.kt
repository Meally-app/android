package com.meally.meally.screens.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.domain.user.User
import com.meally.meally.common.components.BasicButton
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.BasicTextField
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.focusClearer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun SignupScreen(viewModel: SignupViewModel = koinViewModel()) {
    val user = viewModel.userFlow.collectAsStateWithLifecycle().value

    SignupScreenStateless(
        user = user,
        onSignupClicked = { email, password -> viewModel.signupNewUser(email, password) },
        onLoginClicked = { email, password -> viewModel.loginUser(email, password) },
        onLogoutClicked = viewModel::logout,
        onBackendCall = viewModel::test,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SignupScreenStateless(
    user: User?,
    localFocusManager: FocusManager = LocalFocusManager.current,
    onSignupClicked: (String, String) -> Unit = { _, _ -> },
    onLoginClicked: (String, String) -> Unit = { _, _ -> },
    onLogoutClicked: () -> Unit = {},
    onBackendCall: () -> Unit = {},
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp)
                .focusClearer(localFocusManager),
    ) {
        BasicTextField(
            text = email,
            onTextChanged = { email = it },
        )
        VerticalSpacer(24.dp)
        BasicTextField(
            text = password,
            onTextChanged = { password = it },
            visualTransformation = PasswordVisualTransformation()
        )
        VerticalSpacer(24.dp)
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BasicButton(
                text = "Signup",
                onClick = { onSignupClicked(email, password) },
            )
            BasicButton(
                text = "Login",
                onClick = { onLoginClicked(email, password) },
            )
            BasicButton(
                text = "Logout",
                onClick = onLogoutClicked,
            )
//            BasicButton(
//                text = "Backend call",
//                onClick = onBackendCall,
//            )
        }
        VerticalSpacer(12.dp)
        BasicText(
            text = user.toString(),
            style = Typography.body1,
            modifier = Modifier.padding(horizontal = 24.dp),
        )
    }
}

@Preview
@Composable
private fun SignupScreenPreview() {
    MeallyTheme {
        SignupScreenStateless(
            user = User("1", "aaa", "a"),
        )
    }
}
