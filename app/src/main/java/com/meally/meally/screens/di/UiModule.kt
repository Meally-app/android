package com.meally.meally.screens.di

import com.meally.meally.screens.auth.signup.signupModule
import com.meally.meally.screens.barcodeScan.di.barcodeScanModule
import com.meally.meally.screens.browseMeals.di.browseMealsModule
import com.meally.meally.screens.exercise.di.exerciseModule
import com.meally.meally.screens.foodEntry.di.foodEntryModule
import com.meally.meally.screens.foodEntryOptions.di.foodEntryOptionsModule
import com.meally.meally.screens.foodInfo.di.foodInfoModule
import com.meally.meally.screens.homeTab.di.homeTabModule
import com.meally.meally.screens.mealDetails.di.mealDetailsModule
import com.meally.meally.screens.mealEntry.di.mealEntryModule
import com.meally.meally.screens.recentFood.di.recentFoodModule
import com.meally.meally.screens.searchFood.di.searchFoodModule
import com.meally.meally.screens.startup.di.startupModule
import com.meally.meally.screens.weightEntry.di.weightEntryModule
import com.meally.meally.screens.userGraph.di.userGraphModule
import com.meally.meally.screens.userMeals.di.userMealsModule

val screensModule =
    barcodeScanModule +
    signupModule +
    startupModule +
    foodInfoModule +
    foodEntryOptionsModule +
    foodEntryModule +
    homeTabModule +
    weightEntryModule +
    userGraphModule +
    searchFoodModule +
    recentFoodModule +
    exerciseModule +
    browseMealsModule +
    userMealsModule +
    mealDetailsModule +
    mealEntryModule
