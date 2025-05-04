package com.meally.meally.screens.startup.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.startup.viewModel.StartupViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@Destination(start = true)
@Composable
fun StartupScreen(viewModel: StartupViewModel = koinViewModel()) {
    StartupScreenStateless()

    BackHandler {
        // disable back navigation
    }
}

@Composable
private fun StartupScreenStateless() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .systemBarsPadding(),
    ) {
        BasicText(
            text = "Meally",
            style =
                Typography.h1.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                ),
        )
        VerticalSpacer(32.dp)
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview
@Composable
private fun StartupScreenPreview() {
    MeallyTheme {
        StartupScreenStateless()
    }
}
