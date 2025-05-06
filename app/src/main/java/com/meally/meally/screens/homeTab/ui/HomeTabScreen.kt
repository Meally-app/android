package com.meally.meally.screens.homeTab.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.domain.diary.DiaryEntry
import com.meally.domain.food.Food
import com.meally.domain.mealType.MealType
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.destinations.FoodEntryOptionsScreenDestination
import com.meally.meally.screens.destinations.SignupScreenDestination
import com.meally.meally.screens.homeTab.viewModel.HomeTabViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.time.LocalDate

val GOAL_CALORIES = 2000

@Destination
@Composable
fun HomeTabScreen(
    navigator: Navigator = koinInject(),
    viewModel: HomeTabViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    HomeTabScreenStateless(
        state = state,
        onAddClicked = {
            navigator.navigate(FoodEntryOptionsScreenDestination)
        },
        onProfileClicked = {
            navigator.navigate(SignupScreenDestination)
        },
    )

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
}

@Composable
fun HomeTabScreenStateless(
    state: List<DiaryEntry>,
    onAddClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {},
) {

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            AppBar(
                leadingIconResource = R.drawable.ic_profile,
                onLeadingIconClicked = onProfileClicked,
            )
            Content(
                state = state,
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

@Composable
fun ColumnScope.Content(
    state: List<DiaryEntry>,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {

        val consumed = state.sumOf { it.food.calories * it.amount / 100.0 }.toInt()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            DonutPieChart(
                slices = listOf(consumed.toFloat() to MaterialTheme.colorScheme.primary, GOAL_CALORIES - consumed.toFloat() to MaterialTheme.colorScheme.background),
                strokeWidth = 45f,
                modifier = Modifier.size(120.dp)
            )

            BasicText(
                text = "${GOAL_CALORIES - consumed}\nLeft",
                style = Typography.h3.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            )
        }

        VerticalSpacer(16.dp)

        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            DescriptionCard(
                icon = R.drawable.ic_flag,
                label = GOAL_CALORIES.toString(),
                modifier = Modifier.weight(1f)
            )
            DescriptionCard(
                icon = R.drawable.ic_fire,
                label = "0",
                modifier = Modifier.weight(1f)
            )
            DescriptionCard(
                icon = R.drawable.ic_cutlery,
                label = consumed.toString(),
                modifier = Modifier.weight(1f)
            )

        }

        BasicText(
            text = "Eaten today",
            style = Typography.h3,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 32.dp)
        )

        HorizontalDivider(Modifier.padding(vertical = 16.dp))

        FoodList(
            state
        )

        VerticalSpacer(40.dp)
    }
}

@Composable
fun FoodList(
    list: List<DiaryEntry>,
    modifier: Modifier = Modifier
) {

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ){
        list.sortedBy { it.mealType.orderInDay }.forEach {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ){
                Column {
                    BasicText(
                        text = it.food.name,
                        style = Typography.h3
                    )
                    VerticalSpacer(4.dp)
                    BasicText(
                        text = it.mealType.name.replaceFirstChar { it.uppercase() },
                        style = Typography.body2
                    )
                }

                BasicText(
                    text = "${(it.food.calories * it.amount / 100.0).toInt()} kcal",
                    style = Typography.body1
                )
            }
        }
    }

}

@Composable
fun DescriptionCard(
    @DrawableRes icon: Int,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .padding(8.dp),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        VerticalSpacer(4.dp)
        BasicText(
            text = label,
            style = Typography.h3.copy(
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
fun DonutPieChart(
    slices: List<Pair<Float, Color>>, // values and their colors
    modifier: Modifier = Modifier,
    strokeWidth: Float = 40f // Thickness of the ring
) {
    val total = slices.sumOf { it.first.toDouble() }.toFloat()
    val sweepAngles = slices.map { (it.first / total) * 360f }

    Canvas(modifier = modifier) {
        var startAngle = -90f

        sweepAngles.forEachIndexed { index, angle ->
            drawArc(
                color = slices[index].second,
                startAngle = startAngle,
                sweepAngle = angle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )
            startAngle += angle
        }
    }
}

@Preview
@Composable
private fun HomeTabPreview() {
    MeallyTheme {
        HomeTabScreenStateless(
            state = buildList {
                repeat(5) {
                    add(
                        DiaryEntry(
                            food = Food.Empty.copy(
                                name = "Food name",
                                calories = 140.0,
                            ),
                            mealType = MealType(
                                name = "Breakfast",
                                orderInDay = 1,
                            ),
                            amount = 100.0,
                            date = LocalDate.now(),
                        )
                    )
                }
            },
        )
    }
}
