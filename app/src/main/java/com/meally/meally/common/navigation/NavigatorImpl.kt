package com.meally.meally.common.navigation

import android.util.Log
import androidx.navigation.NavOptionsBuilder
import com.meally.meally.screens.destinations.HomeTabScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class NavigatorImpl(
    private val navHostController: NavHostControllerProvider,
) : Navigator,
    CoroutineScope by MainScope() {
    override fun navigate(
        direction: Direction,
        builder: NavOptionsBuilder.() -> Unit,
    ) {
        launch {
            navHostController.navigate(
                direction = direction,
                builder = builder,
            )
        }
    }

    override fun goBack() {
        launch {
            navHostController.popBackStack()
        }
    }

    override fun goToHome() {
        launch {
            while (navHostController.popBackStack());
            navigate(HomeTabScreenDestination)
        }
    }
}
