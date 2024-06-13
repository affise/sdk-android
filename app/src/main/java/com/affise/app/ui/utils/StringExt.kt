package com.affise.app.ui.utils


private val camelPattern = "(?<=.)([A-Z]|\\d+)".toRegex()
fun String.toNormalCase(): String {
    return this.replace(camelPattern, " $0").lowercase().trimStart()
}