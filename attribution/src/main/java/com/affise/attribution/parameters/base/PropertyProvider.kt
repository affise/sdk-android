package com.affise.attribution.parameters.base

/**
 * Base property provider
 */
abstract class PropertyProvider<T> : Provider {

    /**
     * Default value of provider
     */
    abstract val defaultValue: T

    /**
     * Provide data
     */
    abstract fun provide(): T?

    /**
     * Provide data with default value
     */
    fun provideWithDefault(): T = provide() ?: defaultValue
}

/**
 * Base string property provider
 */
abstract class StringPropertyProvider : PropertyProvider<String>() {

    /**
     * Default value of provider
     */
    override val defaultValue = ""
}

/**
 * Base boolean property provider
 */
abstract class BooleanPropertyProvider : PropertyProvider<Boolean>() {

    /**
     * Default value of provider
     */
    override val defaultValue = false
}

/**
 * Base long property provider
 */
abstract class LongPropertyProvider : PropertyProvider<Long>() {

    /**
     * Default value of provider
     */
    override val defaultValue = 0L
}

/**
 * Base string property provider with param
 */
abstract class StringWithParamPropertyProvider : Provider {

    /**
     * Default value of provider
     */
    private val defaultValue = ""

    /**
     * Provide data with param
     */
    abstract fun provideWithParam(param: String): String?

    /**
     * Provide data with param and default value
     */
    fun provideWithParamAndDefault(param: String): String = provideWithParam(param) ?: defaultValue
}