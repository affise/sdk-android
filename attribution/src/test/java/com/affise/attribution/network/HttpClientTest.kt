package com.affise.attribution.network

import com.affise.attribution.exceptions.NetworkException
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

abstract class MockDataSource(url: URL) : HttpsURLConnection(url)

class HttpClientTest {

    @Test
    fun createGoodRequest() {
        val expectedResponse = "This is the expected response text!"

        val data = ""
        val method = HttpClient.Method.POST
        val headers = emptyMap<String, String>()

        val customInputStream: InputStream = ByteArrayInputStream(expectedResponse.toByteArray())
        val customOutputStream: OutputStream = ByteArrayOutputStream()

        val connection: MockDataSource = mockk {
            every { doOutput = true } returns Unit
            every { doInput = true } returns Unit
            every { requestMethod = method.name } returns Unit
            every { useCaches = false } returns Unit
            every { readTimeout = 15000 } returns Unit
            every { connectTimeout = 15000 } returns Unit
            every { inputStream } returns customInputStream
            every { responseCode } returns HttpsURLConnection.HTTP_OK
            every { outputStream } returns customOutputStream
            every { disconnect() } returns Unit
        }

        val url: URL = mockk {
            every { openConnection() } returns connection
        }

        val client = HttpClientImpl()
        val result = client.executeRequest(url, method, data, headers)

        Truth.assertThat(result).isEqualTo(expectedResponse)

        verifyAll {
            connection.doOutput = true
            connection.doInput = true
            connection.requestMethod = method.name
            connection.useCaches = false
            connection.readTimeout = 15000
            connection.connectTimeout = 15000
            connection.inputStream
            connection.responseCode
            connection.outputStream
            connection.disconnect()

            url.openConnection()
        }

    }

    @Test
    fun createBadRequest() {
        val data = ""
        val method = HttpClient.Method.POST
        val headers = emptyMap<String, String>()

        val customOutputStream: OutputStream = ByteArrayOutputStream()

        val connection: MockDataSource = mockk {
            every { doOutput = true } returns Unit
            every { doInput = true } returns Unit
            every { requestMethod = method.name } returns Unit
            every { useCaches = false } returns Unit
            every { readTimeout = 15000 } returns Unit
            every { connectTimeout = 15000 } returns Unit
            every { responseCode } returns HttpsURLConnection.HTTP_BAD_REQUEST
            every { responseMessage } returns ""
            every { outputStream } returns customOutputStream
            every { disconnect() } returns Unit
        }

        val url: URL = mockk {
            every { openConnection() } returns connection
        }

        val client = HttpClientImpl()

        val networkExceptionThrown = try {
            client.executeRequest(url, method, data, headers)
            false
        } catch (throwable: NetworkException) {
            true
        } catch (throwable: Throwable) {
            false
        }

        Truth.assertThat(networkExceptionThrown).isTrue()

        verifyAll {
            connection.doOutput = true
            connection.doInput = true
            connection.requestMethod = method.name
            connection.useCaches = false
            connection.readTimeout = 15000
            connection.connectTimeout = 15000
            connection.responseCode
            connection.responseMessage
            connection.outputStream
            connection.disconnect()

            url.openConnection()
        }

    }
}