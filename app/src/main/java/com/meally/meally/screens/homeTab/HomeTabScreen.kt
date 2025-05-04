package com.meally.meally.screens.homeTab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.meally.meally.R
import com.meally.meally.common.components.BasicButton
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.screens.destinations.FoodEntryOptionsScreenDestination
import com.meally.meally.screens.destinations.SignupScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.compose.koinInject

@Destination
@Composable
fun HomeTabScreen(navigator: Navigator = koinInject()) {
    HomeTabScreenStateless(
        onAddClicked = {
            navigator.navigate(FoodEntryOptionsScreenDestination)
        },
        onLoginClicked = {
            navigator.navigate(SignupScreenDestination)
        },
    )
}

@Composable
fun HomeTabScreenStateless(
    onAddClicked: () -> Unit = {},
    onLoginClicked: () -> Unit = {},
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            BasicButton(
                text = "Barcode Scan",
                onClick = onAddClicked,
            )
            BasicButton(
                text = "Login",
                onClick = onLoginClicked,
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 24.dp, end = 24.dp)
                    .size(56.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .clip(CircleShape)
                    .clickable { onAddClicked() }
                    .padding(8.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview
@Composable
private fun HomeTabPreview() {
    MeallyTheme {
        HomeTabScreenStateless()
    }
}
