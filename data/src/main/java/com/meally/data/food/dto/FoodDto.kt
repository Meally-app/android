package com.meally.data.food.dto

import com.meally.domain.food.Food
import kotlinx.serialization.Serializable

@Serializable
data class FoodDto(
    val id: String,
    val name: String,
    val barcode: String,
    val calories: Double,
    val fat: Double,
    val saturatedFat: Double?,
    val carbs: Double,
    val sugars: Double?,
    val protein: Double,
    val image: ImageResourceDto,
    val unitOfMeasurement: String,
)

@Serializable
data class ImageResourceDto(
    val resourceUrl: String,
)

fun FoodDto.toDomain() =
    Food(
        id = id,
        name = name,
        barcode = barcode,
        calories = calories,
        fat = fat,
        saturatedFat = saturatedFat,
        carbs = carbs,
        sugars = sugars,
        protein = protein,
        imageUrl = image.resourceUrl,
        unitOfMeasurement = Food.UnitOfMeasurement.safeValueOf(unitOfMeasurement),
    )
