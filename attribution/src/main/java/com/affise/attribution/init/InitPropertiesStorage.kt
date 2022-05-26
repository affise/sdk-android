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
     * Update secretId in Affise init properties
     */
    fun updateSecretId(secretId: String)
}