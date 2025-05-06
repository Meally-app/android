package com.meally.meally.screens.di

import com.meally.meally.screens.auth.signup.signupModule
import com.meally.meally.screens.barcodeScan.di.barcodeScanModule
import com.meally.meally.screens.foodEntry.di.foodEntryModule
import com.meally.meally.screens.foodEntryOptions.di.foodEntryOptionsModule
import com.meally.meally.screens.foodInfo.di.foodInfoModule
import com.meally.meally.screens.homeTab.di.homeTabModule
import com.meally.meally.screens.startup.di.startupModule

val screensModule =
    barcodeScanModule +
        signupModule +
        startupModule +
        foodInfoModule +
        foodEntryOptionsModule +
        foodEntryModule +
        homeTabModule
