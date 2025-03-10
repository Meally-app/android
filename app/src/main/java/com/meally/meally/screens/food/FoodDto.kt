package com.meally.meally.screens.food

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OpenFoodFactsProductDto(
    val product: ProductDetailsDto,
    val code: String,
)

@Serializable
data class ProductDetailsDto(
    val nutriments: NutrimentsDto,
    @SerializedName("serving_quantity")
    val servingQuantity: String,
    @SerializedName("serving_quantity_unit")
    val servingQuantityUnit: String,
    @SerializedName("product_name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String?,
)

@Serializable
data class NutrimentsDto(
    @SerializedName("carbohydrates_100g")
    val carbsPer100g: Double,
    @SerializedName("energy_100g")
    val energyKJ: Double,
    @SerializedName("energy-kcal_100g")
    val energyKcal: Double,
    @SerializedName("fat_100g")
    val fatPer100g: Double,
    @SerializedName("proteins_100g")
    val proteinPer100g: Double,
    @SerializedName("saturated-fat_100g")
    val saturatedFatPer100g: Double,
    @SerializedName("sugars_100g")
    val sugarPer100g: Double,
)

fun OpenFoodFactsProductDto.toFood(): FoodItemViewState =
    FoodItemViewState(
        name = product.name,
        imageUrl = product.imageUrl ?: "",
        calories = product.nutriments.energyKcal.toString(),
        fat = product.nutriments.fatPer100g.toString(),
        carbs = product.nutriments.carbsPer100g.toString(),
        protein = product.nutriments.proteinPer100g.toString(),
    )
