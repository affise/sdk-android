package com.affise.attribution.init

/**
 * Storage for initiative sdk property
 */
interface InitPropertiesStorage {

    /**
     * Get Affise init properties
     *
     * @return Affise init properties
     */
    fun getProperties(): AffiseInitProperties?

    /**
     * Set Affise init properties
     */
    fun setProperties(model: AffiseInitProperties)

    /**
     * Update SDK Secret Key in Affise init properties
     */
    fun updateSecretKey(secretKey: String)
}