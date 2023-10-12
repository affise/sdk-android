package com.affise.attribution.debug.validate


enum class ValidationStatus(val status: String) {
    VALID("valid"),
    INVALID_APP_ID("invalid_app_id"),
    INVALID_SECRET_KEY("invalid_secret_key"),
    PACKAGE_NAME_NOT_FOUND("package_name_not_found"),
    NOT_WORKING_ON_PRODUCTION("not_working_on_production"),
    NETWORK_ERROR("network_error"),
    UNKNOWN_ERROR("unknown_error");

    companion object {
        @JvmStatic
        fun from(name: String?): ValidationStatus? = name?.let { value ->
            ValidationStatus.values().firstOrNull { it.status == value }
        }
    }
}