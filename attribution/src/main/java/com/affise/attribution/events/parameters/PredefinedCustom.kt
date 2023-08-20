package com.affise.attribution.events.parameters

class PredefinedCustom {

    private val predefinedParameters = mutableMapOf<String, Any>()

    fun conversionId(orderId: String, productId: String): String {
        val conversionId = "${orderId}_${productId}"
        add(PredefinedString.ORDER_ID, orderId)
        add(PredefinedString.PRODUCT_ID, productId)
        add(PredefinedString.CONVERSION_ID, conversionId)
        return conversionId
    }

    private fun add(parameter: Predefined, value: Any) {
        predefinedParameters[parameter.value()] = value
    }

    internal fun get():Map<String, Any> {
        return predefinedParameters.toMap()
    }

}