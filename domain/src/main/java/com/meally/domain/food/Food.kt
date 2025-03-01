package com.meally.domain.food

data class Food(
    val id: Long,
    val name: String,
) {
    companion object {
        val Empty =
            Food(
                id = 0L,
                name = "",
            )
    }
}
