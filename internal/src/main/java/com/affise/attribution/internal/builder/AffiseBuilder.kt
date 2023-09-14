package com.affise.attribution.internal.builder

import com.affise.attribution.ad.AffiseAdRevenue
import com.affise.attribution.ad.AffiseAdSource
import com.affise.attribution.internal.AffiseApiMethod
import com.affise.attribution.internal.callback.AffiseResult
import com.affise.attribution.internal.ext.opt
import com.affise.attribution.internal.utils.toJSONObject

class AffiseBuilder {

    fun call(
        api: AffiseApiMethod,
        map: Map<String, *>,
        result: AffiseResult
    ) {
        val data = map.opt<Map<*, *>>(api)
        if (data == null) {
            result.error("api [${api.method}]: data not set")
            return
        }

        AffiseBuilderName.from(data.opt<String>(AffiseBuilderProperty.BUILDER_NAME))?.let {
            callBuilder(it, data, result)
        } ?: result.error("api [${api.method}]: builder name not set")
    }

    private fun callBuilder(name: AffiseBuilderName?, map: Map<*, *>, result: AffiseResult) {
        when (name) {
            AffiseBuilderName.AD_REVENUE ->
                callAdRevenue(name, map, result)

            else -> result.notImplemented()
        }
    }

    private fun callAdRevenue(name: AffiseBuilderName, map: Map<*, *>, result: AffiseResult) {
        val source = AffiseAdSource.from(map.opt<String>(AffiseBuilderProperty.SOURCE))
        if (source == null) {
            result.error("api [${name.method}]: not valid source ${map.toJSONObject()}")
            return
        }

        val adRevenue = AffiseAdRevenue(source)

        map.opt<Number>(AffiseBuilderProperty.REVENUE)?.let {
            val currency = map.opt<String>(AffiseBuilderProperty.CURRENCY) ?: ""
            adRevenue.setRevenue(it.toFloat(), currency)
        }

        map.opt<String>(AffiseBuilderProperty.NETWORK)?.let {
            adRevenue.setNetwork(it)
        }

        map.opt<String>(AffiseBuilderProperty.UNIT)?.let {
            adRevenue.setUnit(it)
        }

        map.opt<String>(AffiseBuilderProperty.PLACEMENT)?.let {
            adRevenue.setPlacement(it)
        }

        adRevenue.send()

        result.success(null)
    }
}
