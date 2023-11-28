package com.affise.attribution.usecase

internal interface RemarketingUseCase {

    val locale: String

    val osAndVersion: String

    val device: String

    val build: String
}