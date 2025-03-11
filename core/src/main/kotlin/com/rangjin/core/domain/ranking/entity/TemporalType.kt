package com.rangjin.core.domain.ranking.entity

import java.time.*
import java.time.temporal.Temporal
import kotlin.reflect.KClass

enum class TemporalType (

    private val type: KClass<out Temporal>

) {

    YEARLY(Year::class),
    MONTHLY(YearMonth::class),
    DAILY(LocalDate::class),
    HOURLY(LocalDateTime::class)
    ;

    fun stringToTemporal(string: String): Temporal? {
        return when (this) {
            YEARLY -> Year.of(string.toInt())
            MONTHLY -> YearMonth.parse(string)
            DAILY -> LocalDate.parse(string)
            HOURLY -> LocalDateTime.parse(string)
        }
    }

    companion object {
        fun getTypeByTemporal(temporal: Temporal): TemporalType? {
            return when (temporal) {
                is Year -> YEARLY
                is YearMonth -> MONTHLY
                is LocalDate -> DAILY
                is LocalDateTime -> HOURLY
                else -> null
            }
        }
    }

}