package com.affise.attribution.init

/**
 * Implementation for [InitPropertiesStorage]
 *
 */
class InitPropertiesStorageImpl : InitPropertiesStorage {

    /**
     * AffiseInitProperties cached value
     */
    private var properties: AffiseInitProperties? = null

    /**
     * Get Affise init properties
     *
     * @return Affise init properties
     */
    override fun getProperties() = properties

    /**
     * Set Affise init properties
     */
    override fun setProperties(model: AffiseInitProperties) {
        properties = model
    }

    /**
     * Update SecretId in Affise init properties
     */
    override fun updateSecretId(secretId: String) {
        properties = properties?.copy(secretId = secretId)
    }
}