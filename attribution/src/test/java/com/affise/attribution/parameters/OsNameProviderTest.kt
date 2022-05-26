package com.affise.attribution.parameters

import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Test for [OsNameProvider]
 */
@RunWith(Parameterized::class)
class OsNameProviderTest(
    private val apiLevel: Int,
    private val expectedName: String
) {

    @Test
    fun `verify code name is generated from api version`() {
        val buildConfigPropertiesProvider: BuildConfigPropertiesProvider = mockk {
            every { getSDKVersion() } returns apiLevel
        }
        val provider = OsNameProvider(buildConfigPropertiesProvider)
        Truth.assertThat(provider.provide()).isEqualTo(expectedName.ifEmpty { null })
        verifyAll {
            buildConfigPropertiesProvider.getSDKVersion()
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "when api level is {0} codename should be {1}")
        fun data(): List<Array<Any>> {
            return arrayListOf(
                createParams(apiLevel = 99, expectedName = ""),
                createParams(apiLevel = 31, expectedName = "Android12"),
                createParams(apiLevel = 30, expectedName = "Android11"),
                createParams(apiLevel = 29, expectedName = "Android10"),
                createParams(apiLevel = 28, expectedName = "Pie"),
                createParams(apiLevel = 27, expectedName = "Oreo"),
                createParams(apiLevel = 26, expectedName = "Oreo"),
                createParams(apiLevel = 25, expectedName = "Nougat"),
                createParams(apiLevel = 24, expectedName = "Nougat"),
                createParams(apiLevel = 23, expectedName = "Marshmallow"),
                createParams(apiLevel = 22, expectedName = "Lollipop"),
                createParams(apiLevel = 21, expectedName = "Lollipop"),
                createParams(apiLevel = 19, expectedName = "KitKat"),
                createParams(apiLevel = 18, expectedName = "Jelly Bean"),
                createParams(apiLevel = 17, expectedName = "Jelly Bean"),
                createParams(apiLevel = 16, expectedName = "Jelly Bean"),
                createParams(apiLevel = 15, expectedName = "Ice Cream Sandwich"),
                createParams(apiLevel = 14, expectedName = "Ice Cream Sandwich"),
                createParams(apiLevel = 13, expectedName = "Honeycomb"),
                createParams(apiLevel = 12, expectedName = "Honeycomb"),
                createParams(apiLevel = 11, expectedName = "Honeycomb"),
                createParams(apiLevel = 10, expectedName = "Gingerbread"),
                createParams(apiLevel = 9, expectedName = "Gingerbread"),
                createParams(apiLevel = 8, expectedName = "Froyo"),
                createParams(apiLevel = 7, expectedName = "Eclair"),
                createParams(apiLevel = 6, expectedName = "Eclair"),
                createParams(apiLevel = 5, expectedName = "Eclair"),
                createParams(apiLevel = 4, expectedName = "Donut"),
                createParams(apiLevel = 3, expectedName = "Cupcake"),
                createParams(apiLevel = 2, expectedName = "1.1"),
                createParams(apiLevel = 1, expectedName = "1.0"),
                createParams(apiLevel = 0, expectedName = ""),
            ).toList()
        }

        private fun createParams(
            apiLevel: Int, expectedName: String
        ) = arrayOf<Any>(apiLevel, expectedName)
    }

}