package com.affise.attribution.events.parameters

internal class PredefinedCustom(private val parameters: MutableMap<String, Any>) {

    fun add(parameter: PredefinedString, value: String) {
        when (parameter) {
            PredefinedString.ORDER_ID -> {
                (get(PredefinedString.PRODUCT_ID) as? String)?.let {
                    parameters[CONVERSION_ID] = "${value}${it}"
                }
            }
            PredefinedString.PRODUCT_ID -> {
                (get(PredefinedString.ORDER_ID) as? String)?.let {
                    parameters[CONVERSION_ID] = "${it}${value}"
                }
            }
        }
    }

    private fun hasKey(predefined: Predefined): Boolean {
        return parameters.containsKey(predefined.value())
    }
    private fun get(predefined: Predefined): Any? {
        if (!hasKey(predefined)) return null
        return parameters[predefined.value()]
    }

    companion object {
        private const val CONVERSION_ID = "${Predefined.PREFIX}conversion_id"
    }

}