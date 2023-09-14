package com.affise.attribution.internal


enum class InternalEventName(val eventName: String) {
    SESSION_START("SessionStart");

    companion object {
        @JvmStatic
        fun from(name: String?): InternalEventName? = name?.let { value ->
            InternalEventName.values().firstOrNull { it.eventName == value }
        }
    }
}