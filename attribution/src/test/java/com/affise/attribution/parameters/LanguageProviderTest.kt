package com.affise.attribution.parameters

import com.google.common.truth.Truth
import org.junit.Test
import java.util.Locale

/**
 * Test for [LanguageProvider]
 */
class LanguageProviderTest {

    @Test
    fun verify() {
        Locale.setDefault(Locale.CHINA)
        val provider = LanguageProvider()
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo("zh-CN")
    }
}