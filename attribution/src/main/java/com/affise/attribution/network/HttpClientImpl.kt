package com.affise.attribution.network

import com.affise.attribution.exceptions.NetworkException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HttpClientImpl : HttpClient {

    /**
     * Create request and execute result
     * Executes [method] on url [httpsUrl] with body of [data] and [headers]
     *
     * @return string representation of request
     */
    override fun executeRequest(
        httpsUrl: URL,
        method: HttpClient.Method,
        data: String,
        headers: Map<String, String>
    ): String? {
        var connection: HttpsURLConnection? = null
        var response: String? = null
        try {
            //Create data bytes
            val postDataBytes = data.toByteArray(charset("UTF-8"))

            //Create connection
            connection = httpsUrl.openConnection() as HttpsURLConnection
            connection.doOutput = true
            connection.doInput = true
            connection.requestMethod = method.name
            connection.readTimeout = 15000
            connection.connectTimeout = 15000

            //Add headers
            headers.forEach {
                connection.setRequestProperty(it.key, it.value)
            }
            connection.useCaches = false

            //Send data
            connection.outputStream.use { it.write(postDataBytes) }

            //Get response code
            val responseCode = connection.responseCode

            //Check response code
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                var line: String
                //Create BufferedReader
                val br = BufferedReader(InputStreamReader(connection.inputStream, "UTF-8"))

                //Read response
                while (br.readLine().also { line = it ?: "" } != null) {
                    response = response?.let { it + line } ?: line
                }
            } else {
                throw NetworkException(responseCode, connection.responseMessage)
            }
        } finally {
            //Disconnect
            connection?.disconnect()
        }

        return response
    }
}