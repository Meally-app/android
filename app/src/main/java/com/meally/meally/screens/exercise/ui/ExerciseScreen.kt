package com.meally.meally.screens.exercise.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicButton
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.focusClearer
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.exercise.ui.model.ExerciseEntryViewState
import com.meally.meally.screens.exercise.ui.model.ExerciseScreenViewState
import com.meally.meally.screens.exercise.viewModel.ExerciseViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

data class ExerciseScreenNavArgs(
    val date: LocalDate,
)

@Destination(navArgsDelegate = ExerciseScreenNavArgs::class)
@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = koinViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle().value
    ExerciseScreenStateless(
        state = state,
        onBackClicked = viewModel::goBack,
        onAuthorizeClicked = viewModel::authorize,
    )
}

@Composable
fun ExerciseScreenStateless(
    state: ExerciseScreenViewState,
    onBackClicked: () -> Unit = {},
    onAuthorizeClicked: () -> Unit = {},
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .focusClearer(LocalFocusManager.current),
    ) {
        AppBar(
            leadingIconResource = R.drawable.ic_back,
            onLeadingIconClicked = onBackClicked,
        )
        when {
            state.isLoading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            !state.isAuthorized -> {
                AuthorizationNeeded(onAuthorizeClicked)
            }
            else -> {
                Content(state = state)
            }
        }

    }
}

@Composable
private fun Content(
    state: ExerciseScreenViewState,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        if (state.exercise.isNotEmpty()) {
            state.exercise.forEach {
                ExerciseEntryView(it)
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f),
            ) {
                BasicText(
                    text = "You have no exercise recorded for ${state.date}",
                    style = Typography.body1.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                    ),
                )
            }
        }
    }
}

@Composable
private fun AuthorizationNeeded(
    onAuthorizeClicked: () -> Unit,
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {

        Image(
            painter = painterResource(R.drawable.ic_strava),
            contentDescription = null,
            modifier = Modifier.size(88.dp)
        )

        VerticalSpacer(24.dp)

        BasicText(
            text = "Authorization needed",
            style = Typography.h2.copy(
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            ),
        )

        VerticalSpacer(8.dp)

        BasicText(
            text = "You need to authorize Meally app on Strava to view your exercise data",
            style = Typography.body1.copy(
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            ),
        )

        VerticalSpacer(24.dp)

        BasicButton(
            text = "Authorize",
            onClick = onAuthorizeClicked,
        )

    }

}

@Composable
private fun ExerciseEntryView(
    entry: ExerciseEntryViewState
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
            .padding(12.dp)
    ) {
        Icon(
            painter = painterResource(entry.iconResource),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onBackground,
        )
        HorizontalSpacer(12.dp)
        Column {
            BasicText(
                text = entry.name,
                style = Typography.h3.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
            )
            VerticalSpacer(4.dp)
            BasicText(
                text = entry.calories,
                style = Typography.body2.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun AuthNeededPreview() {
    MeallyTheme {
        ExerciseScreenStateless(
            state = ExerciseScreenViewState(
                isLoading = false,
                isAuthorized = false,
                date = LocalDate.now(),
                exercise = listOf(),
            )
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    MeallyTheme {
        ExerciseScreenStateless(
            state = ExerciseScreenViewState(
                isLoading = true,
                isAuthorized = false,
                date = LocalDate.now(),
                exercise = listOf(),
            )
        )
    }
}

@Preview
@Composable
private fun LoadedPreview() {
    MeallyTheme {
        ExerciseScreenStateless(
            state = ExerciseScreenViewState(
                isLoading = false,
                isAuthorized = true,
                date = LocalDate.now(),
                exercise = listOf(
                    ExerciseEntryViewState(
                        R.drawable.ic_activity_run,
                        name = "Morning Run",
                        calories = "230 kcal",
                    ),
                    ExerciseEntryViewState(
                        R.drawable.ic_activity_swim,
                        name = "Afternoon Swim",
                        calories = "230 kcal",
                    ),
                    ExerciseEntryViewState(
                        R.drawable.ic_fire,
                        name = "Morning Run",
                        calories = "230 kcal",
                    ),
                ),
            )
        )
    }
}

@Preview
@Composable
private fun LoadedEmptyPreview() {
    MeallyTheme {
        ExerciseScreenStateless(
            state = ExerciseScreenViewState(
                isLoading = false,
                isAuthorized = true,
                date = LocalDate.now(),
                exercise = listOf(),
            )
        )
    }
}

