package com.affise.attribution.internal.callback

interface AffiseResult {
    fun success(data: Any?)
    fun error(error: String)
    fun notImplemented()
}