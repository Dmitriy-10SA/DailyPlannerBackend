package com.andef.daily.planner.frontend.core.platform.date

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

expect fun LocalDate.Companion.now(): LocalDate

fun LocalDate.minusDays(days: Long) = this.plus(
    period = DatePeriod(days = -days.toInt())
)

fun LocalDate.minusYears(years: Long) = this.plus(
    period = DatePeriod(years = -years.toInt())
)

fun LocalDate.plusYears(years: Long) = this.plus(
    period = DatePeriod(years = years.toInt())
)