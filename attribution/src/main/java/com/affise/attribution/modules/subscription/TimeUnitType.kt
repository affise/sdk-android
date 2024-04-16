package com.affise.attribution.modules.subscription

enum class TimeUnitType(val value: String) {
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year");

    companion object {
        @JvmStatic
        fun from(name: String?): TimeUnitType? = name?.let { value ->
            TimeUnitType.entries.firstOrNull { it.value == value }
        }
    }
}