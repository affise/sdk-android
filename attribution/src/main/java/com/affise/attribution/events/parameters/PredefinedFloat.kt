package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains Float value
 *
 * @property value the key of parameter
 */
enum class PredefinedFloat(private val value: String): Predefined {
    PREFERRED_PRICE_RANGE("affise_p_preferred_price_range" ),
    PRICE("affise_p_price" ),
    REVENUE("affise_p_revenue" ),
    LAT("affise_p_lat" ),
    LONG("affise_p_long" );

    override fun value(): String = this.value

    companion object {
        @JvmStatic
        fun from(name: String?): PredefinedFloat? = name?.let { value ->
            PredefinedFloat.values().firstOrNull { it.value == value }
        }
    }
}