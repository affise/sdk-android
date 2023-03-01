package com.affise.attribution.platform

internal object SdkPlatform {
    const val ANDROID = "android"
    private const val REACT = "react"
    private const val FLUTTER = "flutter"
    private const val UNITY = "unity"

    private var data = ANDROID

    internal val info: String
        get() = data

    fun react() {
        data = "$REACT $ANDROID"
    }

    fun flutter() {
        data = "$FLUTTER $ANDROID"
    }

    fun unity() {
        data = "$UNITY $ANDROID"
    }
}
