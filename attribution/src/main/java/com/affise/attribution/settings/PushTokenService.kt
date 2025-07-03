package com.affise.attribution.settings

enum class PushTokenService(val service: String) {
    FIREBASE("fms");

    companion object {
        @JvmStatic
        fun from(service: String?): PushTokenService? = service?.let { value ->
            PushTokenService.values().firstOrNull { it.service == value }
        }
    }
}