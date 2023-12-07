package com.affise.attribution.usecase

internal interface RemarketingUseCase {

    val osAndVersion: String

    val device: String

    val build: String

    fun locale(): String
}