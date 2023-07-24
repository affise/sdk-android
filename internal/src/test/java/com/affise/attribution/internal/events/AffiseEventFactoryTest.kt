package com.affise.attribution.internal.events

import android.util.Log
import com.affise.attribution.events.EventName
import com.affise.attribution.events.EventToSerializedEventConverter
import com.affise.attribution.events.predefined.AddToCartEvent
import com.affise.attribution.events.property.AffiseProperty
import com.affise.attribution.events.property.toAffiseEventProperty
import com.affise.attribution.events.subscription.InitialSubscriptionEvent
import com.affise.attribution.events.subscription.SubscriptionParameters
import com.affise.attribution.events.subscription.SubscriptionSubType
import com.affise.attribution.parameters.Parameters
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Before
import org.junit.Test


class AffiseEventFactoryTest {

    @Before
    fun config() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `verify createEvent`() {
        val conv = EventToSerializedEventConverter()

        val eventName = EventName.ADD_TO_CART
        val map = mapOf(
            Parameters.AFFISE_EVENT_NAME to eventName,
            Parameters.AFFISE_EVENT_USER_DATA to "SomeUserData",
            Parameters.AFFISE_EVENT_DATA to mapOf(
                eventName.toAffiseEventProperty(AffiseProperty.TIMESTAMP) to 123456L
            )
        )
        val factory = EventFactory()
        val event = factory.create(map)
        Truth.assertThat(event?.javaClass).isEqualTo(AddToCartEvent::class.java)
    }

    @Test
    fun `verify createSubscriptionEvent`() {
        val map = mapOf(
            Parameters.AFFISE_EVENT_USER_DATA to "SomeUserData",
            Parameters.AFFISE_EVENT_DATA to mapOf(
                SubscriptionParameters.AFFISE_SUBSCRIPTION_EVENT_TYPE_KEY to SubscriptionSubType.AFFISE_SUB_INITIAL_SUBSCRIPTION.typeName,
                "test" to "maybe"
            )
        )
        val factory = EventFactory()
        val event = factory.create(map)
        Truth.assertThat(event?.javaClass).isEqualTo(InitialSubscriptionEvent::class.java)
    }
}