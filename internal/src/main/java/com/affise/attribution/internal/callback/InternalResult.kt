package com.affise.attribution.internal.callback

interface InternalResult {
    fun success(data: Any?)
    fun error(error: String)
    fun notImplemented()
}