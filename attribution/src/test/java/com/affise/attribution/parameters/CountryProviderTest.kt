package com.affise.attribution.parameters

import com.google.common.truth.Truth
import org.junit.Test
import java.util.Locale

/**
 * Test for [CountryProvider]
 */
class CountryProviderTest {
    @Test
    fun provide() {
        Locale.setDefault(Locale("lang", "COUNTRY"))
        val countryProvider = CountryProvider()
        val actual = countryProvider.provide()
        Truth.assertThat(actual).isEqualTo("COUNTRY")
    }
}