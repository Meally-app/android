package com.meally.meally.common.navigation

import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.spec.Direction

interface Navigator {
    fun navigate(
        direction: Direction,
        builder: NavOptionsBuilder.() -> Unit = {},
    )

    fun clearStack()

    fun goBack()

    fun goToHome()
}
