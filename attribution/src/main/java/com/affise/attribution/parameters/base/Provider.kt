package com.affise.attribution.parameters.base

import com.affise.attribution.parameters.ProviderType

interface Provider {
    val order: Float
    val key: ProviderType?
}
