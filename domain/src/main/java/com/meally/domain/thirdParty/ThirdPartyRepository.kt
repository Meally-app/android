package com.meally.domain.thirdParty

import java.time.LocalDate

interface ThirdPartyRepository {

    suspend fun stravaCode(code: String)

    suspend fun syncStrava(date: LocalDate)
}