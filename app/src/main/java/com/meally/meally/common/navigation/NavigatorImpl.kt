package com.meally.meally.common.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.navigation.NavOptionsBuilder
import com.meally.meally.screens.destinations.HomeTabScreenDestination
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class NavigatorImpl(
    private val navHostController: NavHostControllerProvider,
    private val activity: Activity,
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

    override fun clearStack() {
        launch {
            while (navHostController.popBackStack());
        }
    }

    override fun goBack() {
        launch {
            navHostController.popBackStack()
        }
    }

    override fun goToHome() {
        launch {
            clearStack()
            navigate(HomeTabScreenDestination)
        }
    }

    override fun launchIntent(intent: Intent) {
        activity.startActivity(intent)
    }
}
