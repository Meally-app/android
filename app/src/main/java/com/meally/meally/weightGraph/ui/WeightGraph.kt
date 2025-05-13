package com.meally.meally.weightGraph.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.common.time.util.toEpochMillis
import com.meally.meally.common.time.util.toLocalDate
import com.meally.meally.weightGraph.viewModel.WeightGraphViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.core.cartesian.axis.BaseAxis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import kotlinx.coroutines.runBlocking
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun WeightGraph(
    from: LocalDate = LocalDate.now().minusDays(7),
    to: LocalDate = LocalDate.now(),
    viewModel: WeightGraphViewModel = koinViewModel{ parametersOf(from, to) }
) {
    val modelProducer = remember { CartesianChartModelProducer() }

}

@Composable
private fun WeightGraphStateless(
    modelProducer: CartesianChartModelProducer,
    xAxisFormatter: CartesianValueFormatter,
) {
    Box(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    rangeProvider = object : CartesianLayerRangeProvider {
                        override fun getMaxY(minY: Double, maxY: Double, extraStore: ExtraStore): Double {
                            return maxY + 0.2
                        }

                        override fun getMinY(minY: Double, maxY: Double, extraStore: ExtraStore): Double {
                            return minY - 0.2
                        }
                    },
                ),
                startAxis = VerticalAxis.rememberStart(
                ),
                bottomAxis = HorizontalAxis.rememberBottom(
                    valueFormatter = xAxisFormatter,
                ),
            ),
            modelProducer = modelProducer,
        )
    }
}

@Preview
@Composable
private fun WeightGraphPreview() {
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM")
    MeallyTheme {
        val modelProducer = remember { CartesianChartModelProducer() }
        val xValues = buildList<Long> {
            repeat(10) {
                add(LocalDate.now().minusDays(it.toLong()).toEpochMillis())
            }
        }
        val yValues = listOf(74.3, 74.1, 75.0, 73.2, 74.9, 74.3, 74.1, 75.0, 73.2, 74.9)
        runBlocking {
            modelProducer.runTransaction {
                lineSeries { series(xValues, yValues) }
            }
        }
        val formatter = CartesianValueFormatter { _, value, _ ->
            val date = Instant.ofEpochMilli(value.toLong()).toLocalDate()
            dateFormatter.format(date)
        }
        WeightGraphStateless(
            modelProducer = modelProducer,
            xAxisFormatter = formatter
        )
    }
}