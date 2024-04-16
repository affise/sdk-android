package com.affise.attribution.modules.subscription

import java.lang.Exception

sealed class AffiseResult<out T> {
    class Success<T>(val value: T): AffiseResult<T>()
    class Error(val error: Exception): AffiseResult<Nothing>()
}
