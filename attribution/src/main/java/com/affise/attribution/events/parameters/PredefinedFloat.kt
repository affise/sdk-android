package com.affise.attribution.events.parameters

/**
 * Type of predefined parameters contains Float value
 *
 * @property value the key of parameter
 */
enum class PredefinedFloat(private val value: String): Predefined {
    PREFERRED_PRICE_RANGE("preferred_price_range" ),
    PRICE("price" ),
    REVENUE("revenue" ),
    LAT("lat" ),
    LONG("long" );

    override fun value(): String = "${Predefined.PREFIX}${this.value}"

    companion object {
        @JvmStatic
        fun from(name: String?): PredefinedFloat? = name?.let { value ->
            PredefinedFloat.values().firstOrNull { "${Predefined.PREFIX}${it.value}" == value }
        }
    }
}