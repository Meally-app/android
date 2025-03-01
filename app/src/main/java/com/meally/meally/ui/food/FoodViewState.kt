package com.meally.meally.ui.food

data class FoodViewState(
    val isLoadingInitialData: Boolean,
    val food: FoodItemViewState,
    val barcode: String,
) {
    companion object {
        val Initial =
            FoodViewState(
                isLoadingInitialData = true,
                food = FoodItemViewState.Initial,
                barcode = "",
            )
    }
}

data class FoodItemViewState(
    val id: Long,
    val name: String,
) {
    companion object {
        val Initial =
            FoodItemViewState(
                id = 0L,
                name = "",
            )
    }
}
