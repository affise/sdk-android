package com.affise.attribution.events.property

import com.affise.attribution.events.EventName
import com.affise.attribution.utils.toSnakeCase

fun EventName.toAffiseEventProperty(property: AffiseProperty): String = this.eventName.toAffiseEventProperty(property.type)

fun String?.toAffiseEventProperty(property: String? = null): String {
    val name = this?.toSnakeCase() ?: "NAME"
    property?.let {
        return "${PROPERTY_PREFIX}_${name}_${it}"
    }
    return "${PROPERTY_PREFIX}_${name}"
}

private const val PROPERTY_PREFIX = "affise_event"

