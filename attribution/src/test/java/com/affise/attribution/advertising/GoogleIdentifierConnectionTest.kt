package com.affise.attribution.advertising

import android.content.ComponentName
import android.os.IBinder
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

/*
* Test for [GoogleIdentifierConnection]
*/
class GoogleIdentifierConnectionTest {

    @Test
    fun getBinder() {
        val iBinder: IBinder = mockk()

        mockkConstructor(LinkedBlockingQueue::class) {
            every {
                anyConstructed<LinkedBlockingQueue<IBinder>>().poll(10L, TimeUnit.SECONDS)
            } returns iBinder

            val connection = GoogleIdentifierConnection()
            val name: ComponentName = mockk()
            val service: IBinder = mockk()

            connection.onServiceConnected(name, service)

            val result = connection.getBinder()
            Truth.assertThat(result).isEqualTo(iBinder)
        }
    }

    @Test
    fun getBinderAgain() {
        val name: ComponentName = mockk()
        val service: IBinder = mockk()

        val connection = GoogleIdentifierConnection()
        connection.onServiceConnected(name, service)

        connection.getBinder()

        Assert.assertThrows(Throwable::class.java) {
            connection.getBinder()
        }
    }
}