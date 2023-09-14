package com.affise.attribution.ad

import com.affise.attribution.events.parameters.PredefinedFloat
import com.affise.attribution.events.parameters.PredefinedString
import com.affise.attribution.events.predefined.AdRevenueEvent

class AffiseAdRevenue(source: AffiseAdSource) {

    private val event: AdRevenueEvent = AdRevenueEvent()

    init {
        event.addPredefinedParameter(PredefinedString.SOURCE, source.type)
    }

    fun setRevenue(revenue: Float, currency: String): AffiseAdRevenue {
        event.addPredefinedParameter(PredefinedFloat.REVENUE, revenue)
        event.addPredefinedParameter(PredefinedString.CURRENCY, currency)
        return this
    }

    fun setRevenue(revenue: Double, currency: String): AffiseAdRevenue {
        event.addPredefinedParameter(PredefinedFloat.REVENUE, revenue.toFloat())
        event.addPredefinedParameter(PredefinedString.CURRENCY, currency)
        return this
    }

    fun setNetwork(network: String?): AffiseAdRevenue {
        network?.let {
            event.addPredefinedParameter(PredefinedString.NETWORK, it)
        }
        return this
    }

    fun setUnit(unit: String?): AffiseAdRevenue {
        unit?.let {
            event.addPredefinedParameter(PredefinedString.UNIT, it)
        }
        return this
    }

    fun setPlacement(placement: String?): AffiseAdRevenue {
        placement?.let {
            event.addPredefinedParameter(PredefinedString.PLACEMENT, it)
        }
        return this
    }

    fun send() {
        event.send()
    }
}