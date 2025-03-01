package com.meally.meally

import android.app.Application
import com.meally.meally.di.initKoin
import org.koin.android.ext.koin.androidContext

class MeallyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@MeallyApplication) }
    }
}
