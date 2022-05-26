package com.affise.attribution.converter

/**
 * Interface for convert object to another object
 *
 * @param T object type in
 * @param R object type out
 */
interface Converter<in T, out R> {

    /**
     * Convert value [from] to R
     */
    fun convert(from: T): R
}