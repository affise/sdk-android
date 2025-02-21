package com.affise.attribution.module.androidid.parameters

import com.affise.attribution.module.androidid.usecase.AndroidIdUseCase
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [ProviderType.ANDROID_ID]
 *
 * @property app to retrieve contentResolver
 */
class AndroidIdProvider(
    private val useCase: AndroidIdUseCase
) : StringPropertyProvider() {

    override val order: Float = 30.0f
    override val key: ProviderType = ProviderType.ANDROID_ID

    override fun provide(): String? = useCase.getAndroidId()
}