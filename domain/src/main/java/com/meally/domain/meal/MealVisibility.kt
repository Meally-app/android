package com.meally.domain.meal

enum class MealVisibility {
    PUBLIC, PRIVATE, UNKNOWN;

    companion object {
        fun safeValueOf(value: String) = entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: UNKNOWN
    }
}