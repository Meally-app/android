package com.meally.meally.common.navigation

import android.content.Intent
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

    fun launchIntent(intent: Intent)
}
