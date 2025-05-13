package com.meally.meally.common.time.util

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun LocalDate.isToday(): Boolean {
    return this == LocalDate.now()
}

fun LocalDate.toEpochMillis(): Long = this.atStartOfDay(ZoneId.systemDefault()).plusHours(12).toInstant().toEpochMilli()

fun Instant.toLocalDate() = this.atZone(ZoneId.systemDefault()).toLocalDate()