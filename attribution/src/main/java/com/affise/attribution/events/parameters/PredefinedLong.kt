package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains Long value
 *
 * @property value the key of parameter
 */
enum class PredefinedLong(private val value: String): Predefined {
    DATE_A("affise_p_date_a"),
    DATE_B("affise_p_date_b"),
    DEPARTING_ARRIVAL_DATE("affise_p_departing_arrival_date"),
    DEPARTING_DEPARTURE_DATE("affise_p_departing_departure_date"),
    HOTEL_SCORE("affise_p_hotel_score"),
    LEVEL("affise_p_level"),
    MAX_RATING_VALUE("affise_p_max_rating_value"),
    NUM_ADULTS("affise_p_num_adults"),
    NUM_CHILDREN("affise_p_num_children"),
    NUM_INFANTS("affise_p_num_infants"),
    PREFERRED_NUM_STOPS("affise_p_preferred_num_stops"),
    PREFERRED_STAR_RATINGS("affise_p_preferred_star_ratings"),
    QUANTITY("affise_p_quantity"),
    RATING_VALUE("affise_p_rating_value"),
    RETURNING_ARRIVAL_DATE("affise_p_returning_arrival_date"),
    RETURNING_DEPARTURE_DATE("affise_p_returning_departure_date"),
    SCORE("affise_p_score"),
    TRAVEL_START("affise_p_travel_start"),
    TRAVEL_END("affise_p_travel_end"),
    USER_SCORE("affise_p_user_score"),
    EVENT_START("affise_p_event_start"),
    EVENT_END("affise_p_event_end"),
    NUM_ITEMS("numItems");

    override fun value(): String = this.value
}