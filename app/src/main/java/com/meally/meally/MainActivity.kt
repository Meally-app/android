package com.meally.meally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.meally.meally.common.navigation.NavHostControllerProvider
import com.meally.meally.common.theme.MeallyTheme
import com.meally.meally.screens.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    DestinationsNavHost(
        navGraph = NavGraphs.root,
        navController = navHostController,
    )
}

@Composable
fun InjectNavHostController(
    navHostController: NavHostController,
    navigatorProvider: NavHostControllerProvider = koinInject(),
) {
    navigatorProvider.inject(navHostController)
}
