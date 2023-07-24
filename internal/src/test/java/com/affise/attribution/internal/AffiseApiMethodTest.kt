package com.affise.attribution.internal

import com.google.common.truth.Truth
import org.junit.Test

class AffiseApiMethodTest {

    @Test
    fun `verify AffiseApiMethod`() {
        val result = AffiseApiMethod.from(AffiseApiMethod.INIT.method)
        Truth.assertThat(result).isEqualTo(AffiseApiMethod.INIT)
    }


    @Test
    fun `verify AffiseApiMethod undefined`() {
        val result = AffiseApiMethod.from("test")
        Truth.assertThat(result).isEqualTo(null)
    }
}