package com.affise.attribution.parameters

import android.os.Build
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.parameters.base.StringPropertyProvider

/**
 * Provider for parameter [Parameters.OS_NAME]
 *
 * Os name is generating based on [build-numbers](https://source.android.com/source/build-numbers.html)
 */
class OsNameProvider(
    private val buildConfigPropertiesProvider: BuildConfigPropertiesProvider
) : StringPropertyProvider() {

    override val order: Float = 43.0f
    override val key: String = Parameters.OS_NAME

    /**
     * Returns release name, like: honeycomb, kitkat
     */
    override fun provide(): String? = buildConfigPropertiesProvider.getSDKVersion().toCodeName()

    /**
     * Get code name from Build.VERSION_CODES
     */
    private fun Int.toCodeName() = when (this) {
        Build.VERSION_CODES.S -> "Android12"
        Build.VERSION_CODES.R -> "Android11"
        Build.VERSION_CODES.Q -> "Android10"
        Build.VERSION_CODES.P -> "Pie"
        Build.VERSION_CODES.O_MR1 -> "Oreo"
        Build.VERSION_CODES.O -> "Oreo"
        Build.VERSION_CODES.N_MR1 -> "Nougat"
        Build.VERSION_CODES.N -> "Nougat"
        Build.VERSION_CODES.M -> "Marshmallow"
        Build.VERSION_CODES.LOLLIPOP_MR1 -> "Lollipop"
        Build.VERSION_CODES.LOLLIPOP -> "Lollipop"
        Build.VERSION_CODES.KITKAT_WATCH -> "Lollipop"
        Build.VERSION_CODES.KITKAT -> "KitKat"
        Build.VERSION_CODES.JELLY_BEAN_MR2 -> "Jelly Bean"
        Build.VERSION_CODES.JELLY_BEAN_MR1 -> "Jelly Bean"
        Build.VERSION_CODES.JELLY_BEAN -> "Jelly Bean"
        Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 -> "Ice Cream Sandwich"
        Build.VERSION_CODES.ICE_CREAM_SANDWICH -> "Ice Cream Sandwich"
        Build.VERSION_CODES.HONEYCOMB_MR2 -> "Honeycomb"
        Build.VERSION_CODES.HONEYCOMB_MR1 -> "Honeycomb"
        Build.VERSION_CODES.HONEYCOMB -> "Honeycomb"
        Build.VERSION_CODES.GINGERBREAD_MR1 -> "Gingerbread"
        Build.VERSION_CODES.GINGERBREAD -> "Gingerbread"
        Build.VERSION_CODES.FROYO -> "Froyo"
        Build.VERSION_CODES.ECLAIR_MR1 -> "Eclair"
        Build.VERSION_CODES.ECLAIR_0_1 -> "Eclair"
        Build.VERSION_CODES.ECLAIR -> "Eclair"
        Build.VERSION_CODES.DONUT -> "Donut"
        Build.VERSION_CODES.CUPCAKE -> "Cupcake"
        Build.VERSION_CODES.BASE_1_1 -> "1.1"
        Build.VERSION_CODES.BASE -> "1.0"
        else -> null
    }
}