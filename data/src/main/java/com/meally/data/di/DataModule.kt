package com.meally.data.di

import com.meally.data.diary.di.diaryModule
import com.meally.data.food.di.foodModule
import com.meally.data.remote.network.di.networkModule
import com.meally.data.user.di.userModule

val dataModule = foodModule + networkModule + userModule + diaryModule
