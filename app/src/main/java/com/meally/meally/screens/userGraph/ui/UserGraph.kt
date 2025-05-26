package com.meally.meally.screens.userGraph.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meally.meally.R
import com.meally.meally.common.components.AppBar
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.components.OutlinedBasicButton
import com.meally.meally.common.components.VerticalSpacer
import com.meally.meally.common.components.datePicker.DateRangePickerModal
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.theme.Typography
import com.meally.meally.common.time.util.toEpochMillis
import com.meally.meally.common.time.util.toLocalDate
import com.meally.meally.screens.homeTab.ui.Content
import com.meally.meally.screens.userGraph.ui.model.UserGraphViewState
import com.meally.meally.screens.userGraph.viewModel.UserGraphViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEnd
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.rememberHorizontalLegend
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.CartesianLayerPadding
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.LegendItem
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.runBlocking
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Destination
@Composable
fun UserGraphScreen(
    viewModel: UserGraphViewModel = koinViewModel()
) {

    val state = viewModel.viewState.collectAsStateWithLifecycle().value

    UserGraphScreenStateless(
        state = state,
        onBackClicked = viewModel::goBack,
        onDateRangeChanged = viewModel::dateRangeChanged,
    )

}

@Composable
fun UserGraphScreenStateless(
    state: UserGraphViewState,
    onBackClicked: () -> Unit = {},
    onDateRangeChanged: (LocalDate, LocalDate) -> Unit = { _, _ -> },
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
                .verticalScroll(rememberScrollState())
        ) {
            AppBar(
                leadingIconResource = R.drawable.ic_back,
                onLeadingIconClicked = onBackClicked,
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                )
            } else {
                Content(
                    state = state,
                    onOpenDatePicker = { isDatePickerShown = true },
                )
            }
        }
    }

    if (isDatePickerShown) {
        DateRangePickerModal(
            startDate = state.startDate,
            endDate = state.endDate,
            onDateSelected = { start, end -> onDateRangeChanged(start, end) },
            onDismiss = { isDatePickerShown = false }
        )
    }

}

@Composable
fun Content(
    state: UserGraphViewState,
    onOpenDatePicker: () -> Unit,
) {
    Column (
        modifier = Modifier.padding(horizontal = 24.dp)
    ){
        LineGraph(
            xValues = state.dates,
            yValuesStart = state.firstYValues,
            yValuesEnd = state.secondsYValues,
            yStartTitle = state.firstValuesTitle,
            yEndTitle = state.secondValuesTitle,
            xAxisFormatter = cartesianDateFormatter,
        )

        VerticalSpacer(24.dp)

        OutlinedBasicButton(
            onClick = onOpenDatePicker,
            content = {
                BasicText(
                    text = "Enter range",
                    style = Typography.body1,
                )
            }
        )
    }

}

@Composable
fun LineGraph(
    xValues: List<Double>,
    yValuesStart: List<Double>,
    yValuesEnd: List<Double>? = null,
    yStartTitle: String,
    yEndTitle: String? = null,
    xAxisFormatter: CartesianValueFormatter? = null,
) {

    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(xValues, yValuesStart, yValuesEnd) {
        modelProducer.runTransaction {
            lineSeries {
                series(xValues, yValuesStart)
            }
            yValuesEnd?.let {
                lineSeries {
                    series(xValues, it)
                }
            }
            extras { it[LegendLabelKey] = setOf(yStartTitle, yEndTitle ?: "") }
        }
    }
    LineGraphStateless(
        modelProducer = modelProducer,
        xAxisFormatter = xAxisFormatter ?: cartesianDateFormatter,
        showEndYAxis = yValuesEnd != null,
        startTitle = yStartTitle,
        endTitle = yEndTitle,
    )
}

@Composable
private fun LineGraphStateless(
    startTitle: String,
    endTitle: String? = null,
    modelProducer: CartesianChartModelProducer,
    showEndYAxis: Boolean,
    xAxisFormatter: CartesianValueFormatter,
) {
    val legendItemLabelComponent = rememberTextComponent(MaterialTheme.colorScheme.onBackground)
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.tertiary

    val charts = buildList<LineCartesianLayer> {

        add(
            rememberLineCartesianLayer(
                lineProvider = LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(LineCartesianLayer.LineFill.single(fill(primaryColor)))
                ),
                rangeProvider = defaultRangeProvider,
                verticalAxisPosition = Axis.Position.Vertical.Start,
            )
        )
        if (showEndYAxis) {
            add(
                rememberLineCartesianLayer(
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        LineCartesianLayer.rememberLine(LineCartesianLayer.LineFill.single(fill(secondaryColor)))
                    ),
                    rangeProvider = defaultRangeProvider,
                    verticalAxisPosition = Axis.Position.Vertical.End,
                )
            )
        }
    }
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                *charts.toTypedArray(),
                startAxis = VerticalAxis.rememberStart(
                    itemPlacer = VerticalAxis.ItemPlacer.step({ 0.5 }),
                    titleComponent = legendItemLabelComponent,
                    title = startTitle,
                ),
                endAxis = if (showEndYAxis) VerticalAxis.rememberEnd(
                    itemPlacer = VerticalAxis.ItemPlacer.step({ 50.0 }),
                    titleComponent = legendItemLabelComponent,
                    title = endTitle,
                ) else null,
                bottomAxis = HorizontalAxis.rememberBottom(
                    label = rememberAxisLabelComponent(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    valueFormatter = xAxisFormatter,
                ),
                layerPadding = { CartesianLayerPadding(unscalableEndDp = 200f) },
                legend = rememberHorizontalLegend(
                    items = { extraStore ->
                        extraStore[LegendLabelKey].forEachIndexed { index, label ->
                            val color = if (index == 0) primaryColor else secondaryColor
                            add(LegendItem(
                                shapeComponent(fill(color), CorneredShape.Pill),
                                legendItemLabelComponent,
                                label,
                            ))
                        }
                    }
                )
            ),
            modelProducer = modelProducer,
        )
    }
}

private val defaultRangeProvider = object : CartesianLayerRangeProvider {
    override fun getMaxY(minY: Double, maxY: Double, extraStore: ExtraStore): Double {
        return if (minY < 200 && maxY < 200) {
            kotlin.math.ceil(maxY) // round up to nearest larger 1
        } else {
            kotlin.math.ceil(maxY / 50.0) * 50.0 // round up to nearest larger 50
        }
    }

    override fun getMinY(minY: Double, maxY: Double, extraStore: ExtraStore): Double {
        return if (minY < 200 && maxY < 200) {
            kotlin.math.floor(minY) // round down to nearest smaller 1
        } else {
            kotlin.math.floor(minY / 50.0) * 50.0 // round down to nearest smaller 50
        }
    }
}
private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM")

private val cartesianDateFormatter = CartesianValueFormatter { _, value, _ ->
    val date = Instant.ofEpochMilli(value.toLong()).toLocalDate()
    dateFormatter.format(date)
}

private val LegendLabelKey = ExtraStore.Key<Set<String>>()


@Preview
@Composable
private fun SingleYAxisPreview() {
    MeallyTheme(darkTheme = false){
        val modelProducer = remember { CartesianChartModelProducer() }
        val xValues = buildList<Long> {
            repeat(10) {
                add(LocalDate.now().minusDays(it.toLong()).toEpochMillis())
            }
        }
        val weights = listOf(74.3, 74.1, 75.0, 73.2, 74.9, 74.3, 74.1, 75.0, 73.2, 74.9)
        runBlocking {
            modelProducer.runTransaction {
                lineSeries {
                    series(xValues, weights)
                }
                extras { it[LegendLabelKey] = setOf("Weight") }
            }
        }

        LineGraphStateless(
            modelProducer = modelProducer,
            showEndYAxis = false,
            xAxisFormatter = cartesianDateFormatter,
            startTitle = "Weight",
        )
    }
}

@Preview
@Composable
private fun DoubleYAxisPreview() {
    MeallyTheme(darkTheme = false){
        val modelProducer = remember { CartesianChartModelProducer() }
        val xValues = buildList<Long> {
            repeat(10) {
                add(LocalDate.now().minusDays(it.toLong()).toEpochMillis())
            }
        }
        val weights = listOf(74.3, 74.1, 75.0, 73.2, 74.9, 74.3, 74.1, 75.0, 73.2, 74.9)
        val calories = listOf(1900, 2100, 2200, 1960, 2000, 1900, 2100, 2200, 1960, 2000)
        runBlocking {
            modelProducer.runTransaction {
                lineSeries {
                    series(xValues, weights)
                }
                lineSeries {
                    series(xValues, calories)
                }
                extras { it[LegendLabelKey] = setOf("Weight", "Calories") }
            }
        }

        LineGraphStateless(
            modelProducer = modelProducer,
            showEndYAxis = true,
            xAxisFormatter = cartesianDateFormatter,
            startTitle = "Weight",
            endTitle = "Calories",
        )
    }
}