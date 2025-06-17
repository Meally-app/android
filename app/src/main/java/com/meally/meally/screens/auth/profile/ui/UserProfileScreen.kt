package com.meally.meally.screens.auth.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.domain.user.User
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicButton
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.auth.profile.ui.model.UserProfileViewState
import com.meally.meally.screens.auth.profile.viewModel.UserProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value

    UserProfileScreenStateless(
        state = state,
        onLogoutClicked = viewModel::logout,
        onBackClicked = viewModel::goBack,
    )
}

@Composable
private fun UserProfileScreenStateless(
    state: UserProfileViewState,
    onBackClicked: () -> Unit = {},
    onLogoutClicked: () -> Unit = {},
) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        AppBar(
            leadingIconResource = R.drawable.ic_back,
            onLeadingIconClicked = onBackClicked,
        )

        if (state.isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ){
            BasicText(
                text = "Profile",
                style = Typography.h2,
            )

            VerticalSpacer(24.dp)

            BasicText(
                text = "Email: ${state.user.email}",
                style = Typography.body1,
            )

            VerticalSpacer(24.dp)

            BasicText(
                text = "Username: ${state.user.username}",
                style = Typography.body1,
            )

            VerticalSpacer(24.dp)

            BasicButton(
                text = "Logout",
                onClick = onLogoutClicked
            )
        }

    }
}

@Preview
@Composable
private fun UserProfileScreenPreview() {
    MeallyTheme {
        UserProfileScreenStateless(
            state = UserProfileViewState(
                user = User(
                    "",
                    "test@gmail.com",
                    "username"
                ),
                isLoading = false,
            )
        )
    }

}