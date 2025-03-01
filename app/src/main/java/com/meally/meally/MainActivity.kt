package com.meally.meally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.meally.meally.ui.food.FoodScreen
import com.meally.meally.ui.theme.MeallyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeallyTheme {
                FoodScreen()
            }
        }
    }
}
