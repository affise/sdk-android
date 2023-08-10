package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains Long value
 *
 * @property value the key of parameter
 */
enum class PredefinedLong(private val value: String): Predefined {
    AMOUNT("amount"),
    DATE_A("date_a"),
    DATE_B("date_b"),
    DEPARTING_ARRIVAL_DATE("departing_arrival_date"),
    DEPARTING_DEPARTURE_DATE("departing_departure_date"),
    HOTEL_SCORE("hotel_score"),
    LEVEL("level"),
    MAX_RATING_VALUE("max_rating_value"),
    NUM_ADULTS("num_adults"),
    NUM_CHILDREN("num_children"),
    NUM_INFANTS("num_infants"),
    PREFERRED_NUM_STOPS("preferred_num_stops"),
    PREFERRED_STAR_RATINGS("preferred_star_ratings"),
    QUANTITY("quantity"),
    RATING_VALUE("rating_value"),
    RETURNING_ARRIVAL_DATE("returning_arrival_date"),
    RETURNING_DEPARTURE_DATE("returning_departure_date"),
    SCORE("score"),
    TRAVEL_START("travel_start"),
    TRAVEL_END("travel_end"),
    USER_SCORE("user_score"),
    EVENT_START("event_start"),
    EVENT_END("event_end");

    override fun value(): String = "${Predefined.PREFIX}${this.value}"

    companion object {
        @JvmStatic
        fun from(name: String?): PredefinedLong? = name?.let { value ->
            PredefinedLong.values().firstOrNull { "${Predefined.PREFIX}${it.value}" == value }
        }
    }
}