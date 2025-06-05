package com.meally.data.thirdParty

import com.meally.data.remote.network.api.MeallyAppApi
import com.meally.data.util.safeApiCall
import com.meally.domain.thirdParty.ThirdPartyRepository
import java.time.LocalDate

class ThirdPartyRepositoryImpl(
    private val meallyAppApi: MeallyAppApi,
): ThirdPartyRepository {
    override suspend fun stravaCode(code: String) {
        safeApiCall {
            meallyAppApi.sendStravaCode(code)
        }
    }

    override suspend fun syncStrava(date: LocalDate) {
        safeApiCall {
            meallyAppApi.syncStrava(date)
        }
    }
}