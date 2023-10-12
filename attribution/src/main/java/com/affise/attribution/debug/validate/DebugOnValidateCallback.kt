package com.affise.attribution.debug.validate

fun interface DebugOnValidateCallback {

    fun handle(status: ValidationStatus)
}