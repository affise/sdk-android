package com.affise.attribution.events.predefined

/**
 * Type of touch
 */
enum class TouchType(private val type: String) {
    CLICK("CLICK"),
    WEB_TO_APP_AUTO_REDIRECT("WEB_TO_APP_AUTO_REDIRECT"),
    IMPRESSION("IMPRESSION");

    companion object {
        @JvmStatic
        fun from(name: String?): TouchType? = name?.let { type ->
            TouchType.values().firstOrNull { it.type == type }
        }
    }
}

fun String.toTouchType(): TouchType? = TouchType.from(this)
