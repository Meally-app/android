package com.meally.meally.common.navigation

import androidx.navigation.NavOptionsBuilder
import com.meally.meally.ui.food.destinations.FoodScreenDestination
import com.ramcosta.composedestinations.spec.Direction

class NavigatorImpl(
    private val destinationsNavigator: NavHostControllerProvider,
) : Navigator {
    override fun navigate(
        direction: Direction,
        builder: NavOptionsBuilder.() -> Unit,
    ) {
        destinationsNavigator.navigate(
            direction = direction,
            builder = builder,
        )
    }

    override fun goBack() {
        destinationsNavigator.popBackStack()
    }

    override fun goToHome() {
        while (destinationsNavigator.popBackStack());
        navigate(FoodScreenDestination)
    }
}
