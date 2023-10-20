package com.affise.attribution.debug.validate

import com.affise.attribution.converter.ProvidersToJsonStringConverter
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.init.AffiseInitProperties
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.network.CloudConfig
import com.affise.attribution.network.HttpClient
import com.affise.attribution.network.HttpResponse
import com.affise.attribution.parameters.base.Provider
import com.affise.attribution.parameters.factory.PostBackModelFactory
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection


internal class DebugValidateUseCaseImpl(
    postBackModelFactory: PostBackModelFactory,
    private val initProperties: AffiseInitProperties,
    private val logsManager: LogsManager,
    private val httpClient: HttpClient,
    private val sendServiceProvider: ExecutorServiceProvider,
    private val converter: ProvidersToJsonStringConverter,
) : DebugValidateUseCase {

    private val providers: List<Provider> = postBackModelFactory.getRequestProviders()
    private val url: String = CloudConfig.getURL(PATH)

    @Synchronized
    override fun validate(onComplete: DebugOnValidateCallback) {
        if (initProperties.isProduction) {
            onComplete.handle(ValidationStatus.NOT_WORKING_ON_PRODUCTION)
            return
        }

        sendServiceProvider.provideExecutorService().execute {
            sendWithRepeat(onComplete) {
                Thread.sleep(TIME_DELAY_SENDING)
            }
        }
    }

    private fun sendWithRepeat(onComplete: DebugOnValidateCallback, onFailedAttempt: () -> Unit) {
        //attempts to send
        var attempts = ATTEMPTS_TO_SEND

        //Send or not
        var send = false

        //While has attempts and not send
        while (attempts != 0 && !send) {
            val status = getValidationStatus(createRequest())
            if (status != null) {
                onComplete.handle(status)
                //Send is ok
                send = true
            } else {
                //Check attempts
                if (--attempts == 0) {
                    onComplete.handle(ValidationStatus.NETWORK_ERROR)
                } else {
                    onFailedAttempt()
                }
            }
        }
    }

    private fun createRequest(): HttpResponse {
        //Create request
        return httpClient.executeRequest(
            URL(url),
            HttpClient.Method.POST,
            converter.convert(providers),
            CloudConfig.headers
        )
    }

    private fun getValidationStatus(response: HttpResponse): ValidationStatus? {
        if (response.code == 0) {
            return ValidationStatus.NETWORK_ERROR
        }

        try {
            val json = JSONObject(response.body ?: "{}")
            val message = json.optString(KEY, "")

            if (message.equals(INVALID_APP_ID, ignoreCase = true)) {
                return ValidationStatus.INVALID_APP_ID
            }
            if (message.equals(INVALID_CHECK_SUM, ignoreCase = true)) {
                return ValidationStatus.INVALID_SECRET_KEY
            }
            if (message.equals(PACKAGE_NAME_NOT_FOUND, ignoreCase = true)) {
                return ValidationStatus.PACKAGE_NAME_NOT_FOUND
            }
        } catch (_: Exception) {
        }

        if( response.code == HttpsURLConnection.HTTP_OK) {
            return ValidationStatus.VALID
        }
        return null
    }

    companion object {
        private const val KEY = "message"
        private const val INVALID_APP_ID = "Invalid affise_app_id"
        private const val INVALID_CHECK_SUM = "Failed to get application or check sum"
        private const val PACKAGE_NAME_NOT_FOUND = "Package name not found"

        private const val TIME_DELAY_SENDING: Long = 5000L
        private const val ATTEMPTS_TO_SEND = 2

        const val PATH = "postback/validate"
    }
}