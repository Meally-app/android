package com.meally.meally.screens.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meally.domain.barcode.Barcode
import com.meally.meally.common.navigation.Navigator
import com.meally.meally.screens.destinations.FoodInfoScreenDestination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FoodViewModel(
    private val navigator: Navigator,
) : ViewModel() {
    private val isLoading = MutableStateFlow(false)

    private val barcodeState = MutableStateFlow("")

    val viewState =
        combine(barcodeState, isLoading) { barcode, isLoading ->
            FoodViewState(
                isLoading = isLoading,
                barcode = barcode,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = FoodViewState.Initial,
        )

    fun onBarcodeChanged(barcode: Barcode) {
        barcodeState.update { barcode.payload }
        isLoading.update { true }
        loadFoodData(barcode.payload)
    }

    private val retrofitClient =
        Retrofit
            .Builder()
            .baseUrl("https://world.openfoodfacts.org/api/v0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val api = retrofitClient.create(MeallyAppApi::class.java)

    private fun loadFoodData(barcode: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val result = runCatching { api.getProductDetails(barcode).toFood() }
            withContext(Dispatchers.Main) {
                println(result.getOrNull())
                navigator.navigate(FoodInfoScreenDestination(result.getOrNull()))
            }
        }
    }
}
