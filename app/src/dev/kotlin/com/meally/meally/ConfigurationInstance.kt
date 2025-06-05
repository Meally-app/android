package com.meally.meally

import com.meally.domain.common.config.Configuration

val configurationInstance =
    Configuration(
        meallyApiBaseUrl = BuildConfig.MEALLY_API_BASE_URL,
        stravaClientId = BuildConfig.STRAVA_CLIENT_ID.toString(),
        stravaRedirectUrl = BuildConfig.STRAVA_REDIRECT_URI,
    )
