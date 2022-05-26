package com.affise.attribution.build

import android.os.Build

/**
 * Implementation for [BuildConfigPropertiesProvider]
 */
class BuildConfigPropertiesProviderImpl : BuildConfigPropertiesProvider {

    /**
     * @returns SDK_INT from Build.VERSION
     */
    override fun getSDKVersion() = Build.VERSION.SDK_INT

    /**
     * @returns RELEASE from Build.VERSION
     */
    override fun getReleaseName() = Build.VERSION.RELEASE.toString()

    /**
     * @returns SUPPORTED_ABIs form Build
     */
    override fun getSupportedABIs() = Build.SUPPORTED_ABIS.filterNotNull().toList()

    /**
     * @returns HARDWARE from Build
     */
    override fun getHardware() = Build.HARDWARE.toString()

    /**
     * @returns MANUFACTURER from Build
     */
    override fun getManufacturer() = Build.MANUFACTURER.toString()

    /**
     * @returns CODENAME from Build.VERSION
     */
    override fun getCodeName() = Build.VERSION.CODENAME.toString()
}