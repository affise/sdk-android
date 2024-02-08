package com.affise.attribution.parameters

import com.affise.attribution.parameters.providers.LanguageProvider
import com.affise.attribution.usecase.RemarketingUseCase
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import java.util.Locale

/**
 * Test for [LanguageProvider]
 */
class LanguageProviderTest {

    private val remarketingUseCase: RemarketingUseCase = mockk {
        every { locale() } returns "zh-CN"
    }

    @Test
    fun verify() {
        Locale.setDefault(Locale.CHINA)
        val provider = LanguageProvider(remarketingUseCase)
        val actual = provider.provide()
        Truth.assertThat(actual).isEqualTo("zh-CN")
    }
}