package com.affise.attribution.module.meta.parameters

import com.affise.attribution.module.meta.usecase.MetaReferrerUseCase
import com.affise.attribution.parameters.ProviderType
import com.affise.attribution.parameters.base.MapPropertyProvider

/**
 * Provider for parameter [ProviderType.META]
 *
 * @property useCase to retrieve meta referrer data
 */
class MetaProvider(
    private val useCase: MetaReferrerUseCase?
) : MapPropertyProvider() {

    override val order: Float = 71.0f
    override val key: ProviderType = ProviderType.META

    override fun provide(): Map<String, Any>? = useCase?.metaReferrer()
}