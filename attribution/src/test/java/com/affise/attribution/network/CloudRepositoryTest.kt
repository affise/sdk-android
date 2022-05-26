package com.affise.attribution.network

import com.affise.attribution.converter.Converter
import com.affise.attribution.exceptions.CloudException
import com.affise.attribution.exceptions.NetworkException
import com.affise.attribution.network.entity.PostBackModel
import com.affise.attribution.parameters.UserAgentProvider
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyAll
import org.junit.Test
import javax.net.ssl.HttpsURLConnection

class CloudRepositoryTest {

    private val data = ""
    private val models = emptyList<PostBackModel>()
    private val expectedResponse = "This is the expected response text!"
    private val url = "https://url"

    private val client: HttpClient = mockk()

    private val provider: UserAgentProvider = mockk {
        every { provideWithDefault() } returns ""
    }

    private val converter: Converter<List<PostBackModel>, String> = mockk {
        every { convert(models) } returns data
    }

    private val repository: CloudRepositoryImpl = CloudRepositoryImpl(client, provider, converter)

    @Test
    fun `send with good request`() {
        every {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
        } returns expectedResponse

        val sendWithOutError = try {
            repository.send(models, url)
            true
        } catch (throwable: Throwable) {
            false
        }

        Truth.assertThat(sendWithOutError).isTrue()

        verifyAll {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
            provider.provideWithDefault()
            converter.convert(models)
        }
    }

    @Test
    fun `send with second good request`() {
        every {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
        } throws NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "") andThen expectedResponse

        val sendWithOutError = try {
            repository.send(models, url)
            true
        } catch (throwable: Throwable) {
            false
        }

        Truth.assertThat(sendWithOutError).isTrue()

        verifyAll {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
            provider.provideWithDefault()
            converter.convert(models)
        }
    }

    @Test
    fun `send with third good request`() {
        every {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
        } throws NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "") andThenThrows
            NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "") andThen expectedResponse

        val sendWithOutError = try {
            repository.send(models, url)
            true
        } catch (throwable: Throwable) {
            false
        }

        Truth.assertThat(sendWithOutError).isTrue()

        verifyAll {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
            provider.provideWithDefault()
            converter.convert(models)
        }

    }

    @Test
    fun `not send with fourth good request`() {
        every {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
        } throws NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "") andThenThrows
            NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "") andThenThrows
            NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "") andThenThrows
            NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "") andThen expectedResponse

        val sendWithOutError = try {
            repository.send(models, url)
            true
        } catch (throwable: Throwable) {
            Truth.assertThat(throwable).isInstanceOf(CloudException::class.java)
            throwable as CloudException

            Truth.assertThat(throwable.url).isEqualTo(url)
            false
        }

        Truth.assertThat(sendWithOutError).isFalse()

        verifyAll {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
            provider.provideWithDefault()
            converter.convert(models)
        }

    }

    @Test
    fun `not with third bad request`() {
        every {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
        } throws NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "") andThenThrows
            NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "") andThenThrows
            NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "") andThen expectedResponse

        val sendWithOutError = try {
            repository.send(models, url)
            true
        } catch (throwable: Throwable) {
            Truth.assertThat(throwable).isInstanceOf(CloudException::class.java)
            throwable as CloudException

            Truth.assertThat(throwable.url).isEqualTo(url)
            false
        }

        Truth.assertThat(sendWithOutError).isFalse()

        verifyAll {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
            provider.provideWithDefault()
            converter.convert(models)
        }
    }

    @Test
    fun `send with bad request`() {
        every {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
        } throws NetworkException(HttpsURLConnection.HTTP_BAD_REQUEST, "")

        val sendWithOutError = try {
            repository.send(models, url)
            true
        } catch (throwable: Throwable) {
            Truth.assertThat(throwable).isInstanceOf(CloudException::class.java)
            throwable as CloudException

            Truth.assertThat(throwable.url).isEqualTo(url)

            false
        }

        Truth.assertThat(sendWithOutError).isFalse()

        verifyAll {
            client.executeRequest(any(), HttpClient.Method.POST, data, any())
            provider.provideWithDefault()
            converter.convert(models)
        }

    }

}