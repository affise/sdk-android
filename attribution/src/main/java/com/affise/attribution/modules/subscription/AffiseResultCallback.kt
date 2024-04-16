package com.affise.attribution.modules.subscription


fun interface AffiseResultCallback<T> {

    fun handle(result: AffiseResult<T>)
}