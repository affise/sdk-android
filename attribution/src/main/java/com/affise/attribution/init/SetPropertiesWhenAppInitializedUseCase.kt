package com.affise.attribution.init

/**
 * Use case to set properties on library init
 */
interface SetPropertiesWhenAppInitializedUseCase {

    /**
     * Init SetPropertiesWhenAppInitializedUseCase with [initProperties]
     */
    fun init(initProperties: AffiseInitProperties)
}