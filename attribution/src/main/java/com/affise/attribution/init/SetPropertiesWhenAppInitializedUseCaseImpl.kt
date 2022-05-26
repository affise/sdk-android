package com.affise.attribution.init

/**
 * UseCase when user provided properties is stored to storage on app init.
 *
 * @property storage the storage for initiative sdk property.
 */
class SetPropertiesWhenAppInitializedUseCaseImpl(
    private val storage: InitPropertiesStorage
) : SetPropertiesWhenAppInitializedUseCase {

    /**
     * Init SetPropertiesWhenAppInitializedUseCase with [initProperties]
     */
    override fun init(initProperties: AffiseInitProperties) {
        storage.setProperties(initProperties)
    }
}