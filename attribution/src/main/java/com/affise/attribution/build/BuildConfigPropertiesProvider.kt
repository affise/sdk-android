package com.affise.attribution.build

/**
 * Wrapper on android Build config to retrieve data from
 * created for testing purposes
 */
interface BuildConfigPropertiesProvider {

    /**
     * @returns SDK_INT
     */
    fun getSDKVersion(): Int

    /**
     * @returns RELEASE name
     */
    fun getReleaseName(): String?

    /**
     * @returns SUPPORTED_ABIs
     */
    fun getSupportedABIs(): List<String>

    /**
     * @returns HARDWARE
     */
    fun getHardware(): String?

    /**
     * @returns MANUFACTURER
     */
    fun getManufacturer(): String?

    /**
     * @returns CODENAME
     */
    fun getCodeName(): String?
}