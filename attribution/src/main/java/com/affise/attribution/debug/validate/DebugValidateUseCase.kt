package com.affise.attribution.debug.validate

internal interface DebugValidateUseCase {

    fun validate(onComplete: DebugOnValidateCallback)
}