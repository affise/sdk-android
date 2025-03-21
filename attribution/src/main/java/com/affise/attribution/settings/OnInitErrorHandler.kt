package com.affise.attribution.settings


fun interface OnInitErrorHandler {
    fun handle(e: Exception)
}