package com.meally.meally.screens.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.domain.auth.User
import com.meally.meally.common.components.BasicButton
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.components.VerticalSpacer
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
    )
}

@Composable
private fun SignupScreenStateless(
    user: User,
    onSignupClicked: (String, String) -> Unit = { _, _ -> },
    onLoginClicked: (String, String) -> Unit = { _, _ -> },
) {
    var email by remember {
        mutableStateOf("greenbananalp@gmail.com")
    }
    var password by remember {
        mutableStateOf("admin123")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
        )
        VerticalSpacer(24.dp)
        TextField(
            value = password,
            onValueChange = { password = it },
        )
        VerticalSpacer(24.dp)
        Row {
            BasicButton(
                text = "Signup",
                onClick = { onSignupClicked(email, password) },
            )
            HorizontalSpacer(8.dp)
            BasicButton(
                text = "Login",
                onClick = { onLoginClicked(email, password) },
            )
        }
        VerticalSpacer(12.dp)
        BasicText(
            text = user.toString(),
            style = Typography.body1,
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
