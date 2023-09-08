package com.affise.attribution.usecase

internal interface DeviceUseCase {
    fun isRooted(): Boolean

    fun isEmulator(): Boolean
}