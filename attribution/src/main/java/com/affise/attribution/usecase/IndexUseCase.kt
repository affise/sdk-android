package com.affise.attribution.usecase

interface IndexUseCase {
    fun getUuidIndex(): Long
    fun getAffiseEventIdIndex(): Long
}