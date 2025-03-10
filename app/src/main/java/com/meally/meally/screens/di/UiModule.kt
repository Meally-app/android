package com.meally.meally.screens.di

import com.meally.meally.screens.auth.signup.signupModule
import com.meally.meally.screens.food.foodModule

val screensModule = foodModule + signupModule
