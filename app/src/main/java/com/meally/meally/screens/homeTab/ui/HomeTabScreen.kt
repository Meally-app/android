package com.meally.meally.screens.homeTab.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.meally.domain.diary.FoodEntry
import com.meally.domain.food.Food
import com.meally.domain.mealType.MealType
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.HorizontalSpacer
import com.meally.meally.common.components.OutlinedBasicButton
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.datePicker.DatePickerInput
import com.meally.meally.common.components.datePicker.DatePickerModal
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.screens.destinations.ExerciseScreenDestination
import com.meally.meally.screens.destinations.FoodEntryOptionsScreenDestination
import com.meally.meally.screens.destinations.SignupScreenDestination
import com.meally.meally.screens.destinations.UserGraphScreenDestination
import com.meally.meally.screens.destinations.UserMealsScreenDestination
import com.meally.meally.screens.homeTab.ui.model.CaloriesPieChartValues
import com.meally.meally.screens.homeTab.ui.model.FoodListItem
import com.meally.meally.screens.homeTab.ui.model.HomeTabViewState
import com.meally.meally.screens.homeTab.viewModel.HomeTabViewModel
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.time.LocalDate

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
        onDateSelected = viewModel::selectDate,
        onAddWeightClicked = viewModel::addWeight,
        onProfileClicked = {
            navigator.navigate(SignupScreenDestination)
        },
        onMealsClicked = {
            navigator.navigate(UserMealsScreenDestination)
        },
        onExerciseClicked = {
            navigator.navigate(ExerciseScreenDestination(state.selectedDate))
        }
    )

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
}

@Composable
fun HomeTabScreenStateless(
    state: HomeTabViewState,
    onAddClicked: () -> Unit = {},
    onDateSelected: (LocalDate) -> Unit = {},
    onAddWeightClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {},
    onMealsClicked: () -> Unit = {},
    onExerciseClicked: () -> Unit = {},
) {

    var isDatePickerShown by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppBar(
                leadingIconResource = R.drawable.ic_profile,
                onLeadingIconClicked = onProfileClicked,
                trailingIconResource = R.drawable.ic_chef,
                onTrailingIconClicked = onMealsClicked,
            )
            Content(
                state = state,
                onOpenDatePicker = { isDatePickerShown = true },
                onAddWeightClicked = onAddWeightClicked,
                onExerciseClicked = onExerciseClicked,
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

        if (isDatePickerShown) {
            DatePickerModal(
                selectedDate = state.selectedDate,
                onDateSelected = { onDateSelected(it) },
                onDismiss = { isDatePickerShown = false },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Content(
    state: HomeTabViewState,
    onOpenDatePicker: () -> Unit,
    onAddWeightClicked: () -> Unit,
    onExerciseClicked: () -> Unit,
) {

    LazyColumn (
        contentPadding = PaddingValues(bottom = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {

        item ("donut_graph"){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .padding(24.dp)
                    .animateItem()
            ) {
                with(state.caloriesPieChartValues) {
                    DonutPieChart(
                        slices = listOf(
                            consumed to MaterialTheme.colorScheme.primary,
                            remaining to MaterialTheme.colorScheme.background,
                            exercise to MaterialTheme.colorScheme.secondary,
                        ),
                        strokeWidth = 45f,
                        modifier = Modifier.size(120.dp)
                    )
                }

                BasicText(
                    text = "${state.caloriesPieChartValues.remaining.toInt()}\nLeft",
                    style = Typography.h3.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                    )
                )

                if (state.weight != null) {
                    BasicText(
                        text = "${state.weight} kg",
                        style = Typography.body2.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }

            VerticalSpacer(16.dp)
        }

        item ("info_tabs"){
            Row (
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth().animateItem()
            ){
                DescriptionCard(
                    icon = R.drawable.ic_flag,
                    label = state.goalCalories.toString(),
                    modifier = Modifier.weight(1f)
                )
                DescriptionCard(
                    icon = R.drawable.ic_fire,
                    label = state.exerciseCalories.toString(),
                    modifier = Modifier.weight(1f),
                    onClick = onExerciseClicked,
                )
                DescriptionCard(
                    icon = R.drawable.ic_cutlery,
                    label = state.consumedCalories.toString(),
                    modifier = Modifier.weight(1f)
                )

            }
        }

        item ("flexible_row_items"){
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp).animateItem()
            ){
                DatePickerInput(
                    selectedDate = state.selectedDate,
                    onClick = onOpenDatePicker,
                    textStyle = Typography.body2.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                )
                if (state.weight == null) {
                    HorizontalSpacer(8.dp)
                    OutlinedBasicButton(
                        onClick = onAddWeightClicked,
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(R.drawable.ic_plus),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(24.dp)
                            )
                            HorizontalSpacer(4.dp)
                            BasicText(
                                text = "Add weight",
                                style = Typography.body2.copy(
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    }
                }
            }


            HorizontalDivider(Modifier.padding(vertical = 16.dp))
        }

        FoodList(
            state.items
        )

        item {
            VerticalSpacer(40.dp)
        }

    }
}

fun LazyListScope.FoodList(
    list: List<FoodListItem>,
) {
    items(list, key = { it.name + it.calories }) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                .padding(16.dp)
                .animateItem()
        ){
            Column {
                BasicText(
                    text = it.name,
                    style = Typography.h3.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                VerticalSpacer(4.dp)
                BasicText(
                    text = it.mealType.name.replaceFirstChar { it.uppercase() },
                    style = Typography.body2.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }

            BasicText(
                text = "${it.calories} kcal",
                style = Typography.body1.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }

}

@Composable
fun DescriptionCard(
    @DrawableRes icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .then(
                onClick?.let {
                    Modifier.clickable { it() }
                } ?: Modifier
            )
            .padding(8.dp)
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
            state = HomeTabViewState(
                isLoading = false,
                items = buildList {
                    repeat(5) {
                        add(
                            FoodListItem(
                                name = "Food name",
                                mealType = MealType(
                                    name = "Breakfast",
                                    orderInDay = 1,
                                ),
                                calories = (140 + it).toString()
                            )
                        )
                    }
                },
                consumedCalories = 653,
                exerciseCalories = 212,
                goalCalories = 2000,
                selectedDate = LocalDate.now().plusDays(2),
                weight = 75.3,
                caloriesPieChartValues = CaloriesPieChartValues(
                    consumed = 653f,
                    exercise = 212f,
                    remaining = 1559f,
                    total = 2212f,
                )
            )
        )
    }
}
