package com.meally.data.di

import com.meally.data.diary.di.diaryModule
import com.meally.data.exercise.di.exerciseModule
import com.meally.data.food.di.foodModule
import com.meally.data.mealType.di.mealTypeModule
import com.meally.data.meals.di.mealDataModule
import com.meally.data.remote.network.di.networkModule
import com.meally.data.thirdParty.di.thirdPartyModule
import com.meally.data.user.di.userModule
import com.meally.data.weight.di.weightModule

val dataModule = foodModule + networkModule + userModule + diaryModule + mealTypeModule + weightModule + thirdPartyModule + exerciseModule + mealDataModule
