package com.meally.meally.common.navigation.serializer

import com.ramcosta.composedestinations.navargs.DestinationsNavTypeSerializer
import com.ramcosta.composedestinations.navargs.NavTypeSerializer
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@NavTypeSerializer
class LocalDateSerializer : DestinationsNavTypeSerializer<LocalDate> {
    override fun fromRouteString(routeStr: String): LocalDate {
        return LocalDate.parse(routeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    override fun toRouteString(value: LocalDate): String {
        return value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
}