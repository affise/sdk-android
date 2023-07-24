package com.affise.attribution.utils

private val camelPattern = "(?<=.)([A-Z]|\\d+)".toRegex()

fun String.toSnakeCase(): String {
    return this.replace(camelPattern, "_$0").lowercase()
}