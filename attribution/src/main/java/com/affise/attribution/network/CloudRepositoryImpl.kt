package com.affise.attribution.network

import com.affise.attribution.converter.Converter
import com.affise.attribution.exceptions.CloudException
import com.affise.attribution.network.entity.PostBackModel
import com.affise.attribution.parameters.UserAgentProvider
import java.net.URL

internal class CloudRepositoryImpl(
    private val httpClient: HttpClient,
    private val userAgentProvider: UserAgentProvider?,
    private val converter: Converter<@JvmSuppressWildcards List<PostBackModel>, @JvmSuppressWildcards String>
) : CloudRepository {

    /**
     * Send [data] for [url] if [url]
     * @throws CloudException contains throwable for url
     */
    override fun send(data: List<PostBackModel>, url: String) {
        //attempts to send
        var attempts = ATTEMPTS_TO_SEND

        //Send or not
        var send = false

        //While has attempts and not send
        while (attempts != 0 && !send) {
            try {
                //Create request
                createRequest(url, data)

                //Send is ok
                send = true
            } catch (throwable: Throwable) {
                //Check attempts
                if (--attempts == 0) {
                    //Add throwable
                    throw CloudException(url, throwable, ATTEMPTS_TO_SEND, true)
                }
            }
        }
    }

    /**
     * Send [data] to [url]
     */
    private fun createRequest(url: String, data: List<PostBackModel>) {
        //Create request
        httpClient.executeRequest(
            URL(url),
            HttpClient.Method.POST,
            converter.convert(data),
            createHeaders()
        )
    }

    /**
     * Create headers
     */
    private fun createHeaders() = mapOf(
        "User-Agent" to (userAgentProvider?.provideWithDefault() ?: ""),
        "Content-Type" to "application/json; charset=utf-8"
    )

    companion object {
        private const val ATTEMPTS_TO_SEND = 3
    }
}