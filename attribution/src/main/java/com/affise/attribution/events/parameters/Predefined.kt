package com.affise.attribution.events.parameters

interface Predefined {
    fun value(): String

    companion object {
        const val PREFIX = "affise_p_"
    }
}