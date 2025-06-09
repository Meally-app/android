package com.meally.meally.common.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.meally.meally.R
import com.meally.meally.screens.NavGraphs
import com.meally.meally.screens.appCurrentDestinationAsState
import com.meally.meally.screens.destinations.BrowseMealsScreenDestination
import com.meally.meally.screens.destinations.HomeTabScreenDestination
import com.meally.meally.screens.destinations.UserGraphScreenDestination
import com.meally.meally.screens.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@Composable
fun BottomBar(
    navController: NavController,
) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination
    Row (
        modifier = Modifier.fillMaxWidth()
    ){
        BottomBarDestination.entries.forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(destination.direction) {
                        launchSingleTop = true
                    }
                },
                icon = destination.iconRes,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun BottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )
    }

}

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    @DrawableRes val iconRes: Int,
) {
    Home(HomeTabScreenDestination, R.drawable.ic_cutlery),
    Browse(BrowseMealsScreenDestination, R.drawable.ic_search),
    Graphs(UserGraphScreenDestination, R.drawable.ic_graph)
}