package com.meally.meally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.meally.meally.common.components.BottomBar
import com.meally.meally.common.components.BottomBarDestination
import com.meally.meally.common.navigation.NavHostControllerProvider
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.screens.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import org.koin.compose.koinInject
import org.koin.core.context.GlobalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val koin = GlobalContext.get()
        koin.setProperty("activity", this)
        setContent {
            MeallyTheme {
                MeallyApp()
            }
        }
    }
}

@Composable
fun MeallyApp() {
    val navHostController = rememberNavController()
    InjectNavHostController(navHostController)

    val isBottomBarVisible = showBottomBar(navHostController)

    Scaffold (
        bottomBar = {
            if (isBottomBarVisible) {
                BottomBar(
                    navController = navHostController,
                )
            }
        }
    ){ 
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navHostController,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun InjectNavHostController(
    navHostController: NavHostController,
    navigatorProvider: NavHostControllerProvider = koinInject(),
) {
    navigatorProvider.inject(navHostController)
}

@Composable
private fun showBottomBar(navHostController: NavHostController): Boolean {
    val tabDestinations = BottomBarDestination.entries.map { it.direction.route }
    val currentRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route ?: return false
    return tabDestinations.any { it.startsWith(currentRoute, ignoreCase = true) }
}
