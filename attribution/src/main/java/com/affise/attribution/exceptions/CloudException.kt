package com.affise.attribution.exceptions

/**
 * Cloud exception contains network
 *
 * @property url the url where the request was made
 * @property throwable the error per request
 * @property attempts the number of attempts per request
 */
data class CloudException(
    val url: String,
    val throwable: Throwable,
    val attempts: Int,
    val retry: Boolean = false
) : Throwable()