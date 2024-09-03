package com.affise.attribution.internal.data

import com.affise.attribution.deeplink.DeeplinkValue
import com.affise.attribution.internal.ext.opt
import com.affise.attribution.modules.subscription.AffiseProduct
import com.affise.attribution.modules.subscription.AffiseProductPrice
import com.affise.attribution.modules.subscription.AffiseProductSubscriptionDetail
import com.affise.attribution.modules.subscription.AffiseProductType
import com.affise.attribution.modules.subscription.AffiseProductsResult
import com.affise.attribution.modules.subscription.AffisePurchasedInfo
import com.affise.attribution.modules.subscription.AffiseResult
import com.affise.attribution.modules.subscription.TimeUnitType
import com.affise.attribution.network.HttpRequest
import com.affise.attribution.network.HttpResponse

object DataMapper {

    fun fromDeeplinkValue(data: DeeplinkValue): Map<String, *> = mapOf(
        DataName.DEEPLINK to data.deeplink,
        DataName.SCHEME to data.scheme,
        DataName.HOST to data.host,
        DataName.PATH to data.path,
        DataName.PARAMETERS to data.parameters
    )

    fun fromRequest(data: HttpRequest): Map<String, *> = mapOf(
        DataName.METHOD to data.method.toString(),
        DataName.URL to data.url.toString(),
        DataName.HEADERS to data.headers,
        DataName.BODY to data.body,
    )

    fun fromResponse(data: HttpResponse): Map<String, *> = mapOf(
        DataName.CODE to data.code,
        DataName.MESSAGE to data.message,
        DataName.HEADERS to data.headers,
        DataName.BODY to data.body,
    )

    fun fromFetchProductsResult(result: AffiseResult<AffiseProductsResult>): Map<String, Any?> {
        return fromResult(result, ::fromAffiseProductsResult)
    }


    fun fromPurchaseResult(result: AffiseResult<AffisePurchasedInfo>): Map<String, Any?> {
        return fromResult(result, ::fromAffisePurchasedInfo)
    }

    private fun <T> fromResult(result: AffiseResult<T>, transformer: (T) -> Map<String, Any?>?): Map<String, Any?> {
        val map: MutableMap<String, Any?> = mutableMapOf()
        when (result) {
            is AffiseResult.Success -> {
                map[DataName.SUCCESS] = transformer(result.value)
            }

            is AffiseResult.Error -> {
                map[DataName.ERROR] = result.error.message.toString()
            }
        }
        return map
    }

    private fun fromAffiseProductsResult(data: AffiseProductsResult): Map<String, Any?> {
        return mapOf(
            DataName.PRODUCTS to fromProductsList(data.products),
            DataName.INVALID_IDS to data.invalidIds
        )
    }

    private fun fromAffisePurchasedInfo(data: AffisePurchasedInfo): Map<String, Any?> {
        return mapOf(
            DataName.PRODUCT to fromProduct(data.product),
            DataName.ORDER_ID to data.orderId,
            DataName.ORIGINAL_ORDER_ID to data.originalOrderId,
            DataName.PURCHASE to data.purchase.toString(),
        )
    }

    private fun fromProductsList(products: List<AffiseProduct>): List<Map<String, Any?>> {
        return products.mapNotNull { fromProduct(it) }
    }

    private fun fromProduct(product: AffiseProduct?): Map<String, Any?>? {
        product ?: return null
        return mapOf(
            DataName.PRODUCT_ID to product.productId,
            DataName.TITLE to product.title,
            DataName.DESCRIPTION to product.productDescription,
            DataName.PRODUCT_TYPE to product.productType.value,
            DataName.PRICE to fromPrice(product.price),
            DataName.SUBSCRIPTION to fromSubscription(product.subscription),
            DataName.PRODUCT_DETAILS to product.productDetails.toString(),
        )
    }

    private fun fromPrice(price: AffiseProductPrice?): Map<String, Any?>? {
        return price?.let {
            mapOf(
                DataName.VALUE to it.value,
                DataName.CURRENCY_CODE to it.currencyCode,
                DataName.FORMATTED_PRICE to it.formattedPrice,
            )
        }
    }

    private fun fromSubscription(subscription: AffiseProductSubscriptionDetail?): Map<String, Any?>? {
        return subscription?.let {
            mapOf(
                DataName.OFFER_TOKEN to it.offerToken,
                DataName.OFFER_ID to it.offerId,
                DataName.TIME_UNIT to it.timeUnit?.value,
                DataName.NUMBER_OF_UNITS to it.numberOfUnits,
            )
        }
    }

    fun toAffiseProduct(data: Map<String, *>?): AffiseProduct? {
        data ?: return null

        val productId = data.opt<String>(DataName.PRODUCT_ID) ?: return null
        val title = data.opt<String>(DataName.TITLE) ?: return null
        val description = data.opt<String>(DataName.DESCRIPTION) ?: return null
        val productType = data.opt<String>(DataName.PRODUCT_TYPE) ?: return null
        val price = data.opt<Map<String, *>>(DataName.PRICE)
        val subscription = data.opt<Map<String, *>>(DataName.SUBSCRIPTION)
        val productDetails = data.opt<String>(DataName.PRODUCT_DETAILS)

        return AffiseProduct(
            productId = productId,
            title = title,
            productDescription = description,
            productType = toAffiseProductType(productType) ?: AffiseProductType.CONSUMABLE,
            price = toPrice(price),
            subscription = toSubscription(subscription),
            productDetails = productDetails,
        )
    }

    private fun toPrice(data: Map<String, *>?): AffiseProductPrice? {
        data ?: return null

        val value = data.opt<Number>(DataName.VALUE)?.toFloat() ?: return null
        val currencyCode = data.opt<String>(DataName.CURRENCY_CODE) ?: return null
        val formattedPrice = data.opt<String>(DataName.FORMATTED_PRICE) ?: return null

        return AffiseProductPrice(
            value = value,
            currencyCode = currencyCode,
            formattedPrice = formattedPrice,
        )
    }

    private fun toSubscription(data: Map<String, *>?): AffiseProductSubscriptionDetail? {
        data ?: return null

        val offerToken = data.opt<String>(DataName.OFFER_TOKEN) ?: return null
        val offerId = data.opt<String>(DataName.OFFER_ID) ?: return null
        val timeUnit = TimeUnitType.from(data.opt<String>(DataName.TIME_UNIT)) ?: return null
        val numberOfUnits = data.opt<Number>(DataName.NUMBER_OF_UNITS)?.toLong() ?: return null

        return AffiseProductSubscriptionDetail(
            offerToken = offerToken,
            offerId = offerId,
            timeUnit = timeUnit,
            numberOfUnits = numberOfUnits
        )
    }

    fun toAffiseProductType(name: String?) = AffiseProductType.from(name)
}

