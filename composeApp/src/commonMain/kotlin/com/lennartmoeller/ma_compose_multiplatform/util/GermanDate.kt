package com.lennartmoeller.ma_compose_multiplatform.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

class GermanDate {

    private var localDate: LocalDate

    constructor(date: String) {
        if (!date.matches(Regex("""\d{4}-\d{2}-\d{2}"""))) {
            throw IllegalArgumentException("Invalid date format. Expected 'YYYY-MM-DD'.")
        }
        localDate = LocalDate.parse(date)
    }

    constructor(epochMilliseconds: Long) {
        localDate = Instant.fromEpochSeconds(epochMilliseconds / 1000)
            .toLocalDateTime(TimeZone.UTC)
            .date
    }

    constructor() {
        localDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    private fun getMonthName(): String {
        return when (localDate.monthNumber) {
            1 -> "Januar"
            2 -> "Februar"
            3 -> "März"
            4 -> "April"
            5 -> "Mai"
            6 -> "Juni"
            7 -> "Juli"
            8 -> "August"
            9 -> "September"
            10 -> "Oktober"
            11 -> "November"
            else -> "Dezember"
        }
    }

    private fun getWeekdayName(): String {
        return when (localDate.dayOfWeek) {
            DayOfWeek.SUNDAY -> "Sonntag"
            DayOfWeek.MONDAY -> "Montag"
            DayOfWeek.TUESDAY -> "Dienstag"
            DayOfWeek.WEDNESDAY -> "Mittwoch"
            DayOfWeek.THURSDAY -> "Donnerstag"
            DayOfWeek.FRIDAY -> "Freitag"
            else -> "Samstag"
        }
    }

    fun toEpochMillis(): Long {
        return localDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
    }

    override fun toString(): String {
        return localDate.toString()
    }

    fun beautifyDate(
        monthAsNumber: Boolean = true,
        includeYear: Boolean = true,
        twoDigitNumbers: Boolean = true,
        includeWeekday: Boolean = false
    ): String {
        return buildString {
            // weekday
            if (includeWeekday) append("${getWeekdayName()}, ")
            // day
            if (twoDigitNumbers) append(Utility.addLeadingZeros(localDate.dayOfMonth, 2))
            else append(localDate.dayOfMonth.toString())
            append(".")
            // month
            if (monthAsNumber) {
                if (twoDigitNumbers) append(Utility.addLeadingZeros(localDate.monthNumber, 2))
                else append(localDate.monthNumber.toString())
                append(".")
            } else {
                append(" ${getMonthName()}")
            }
            // year
            if (includeYear) {
                if (!monthAsNumber) append(" ")
                append(localDate.year)
            }
        }.trim()
    }
}
