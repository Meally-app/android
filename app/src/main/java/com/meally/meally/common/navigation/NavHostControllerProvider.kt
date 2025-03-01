package com.meally.meally.common.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.spec.Direction

interface NavHostControllerProvider {
    fun inject(navHostController: NavHostController)

    fun navigate(
        direction: Direction,
        onlyIfResumed: Boolean = false,
        builder: NavOptionsBuilder.() -> Unit = {},
    )

    fun popBackStack(): Boolean

    class Default : NavHostControllerProvider {
        private var navHostController: NavHostController? = null

        override fun inject(navHostController: NavHostController) {
            this.navHostController = navHostController
        }

        override fun navigate(
            direction: Direction,
            onlyIfResumed: Boolean,
            builder: NavOptionsBuilder.() -> Unit,
        ) {
            navHostController?.navigate(direction.route, builder)
        }

        override fun popBackStack(): Boolean = navHostController?.popBackStack() ?: false
    }
}
