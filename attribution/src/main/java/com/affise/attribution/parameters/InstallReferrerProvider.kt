package com.affise.attribution.parameters

import android.app.Application
import com.affise.attribution.parameters.base.StringPropertyProvider
import com.affise.attribution.usecase.RetrieveInstallReferrerUseCase
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Provider for parameter [Parameters.REFERRER]
 *
 * @property app to get partner_key in assets
 * @property referrerUseCase usecase to retrieve install begin time
 */
class InstallReferrerProvider(
    private val app: Application,
    private val referrerUseCase: RetrieveInstallReferrerUseCase
) : StringPropertyProvider() {

    override val order: Float = 34.0f
    override val key: String = Parameters.REFERRER

    override fun provide(): String? {
        //Check referrer in partner_key
        val referrer = try {
            //Create InputStream
            app.assets.open("partner_key").use { inputStream ->
                //Create BufferedReader
                BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
                    //Create StringBuilder
                    val builder = StringBuilder()
                    //Create temp line
                    var line: String?

                    //Read file
                    do {
                        line = bufferedReader.readLine()?.also {
                            builder.append(it)
                        }
                    } while (line != null)

                    //Crete String result
                    builder.toString()
                }
            }
        } catch (throwable: Throwable) {
            //logsManager.addDeviceError(throwable)
            null
        }

        //if partner_key is empty or null use installReferrer
        return if (referrer.isNullOrEmpty()) {
            referrerUseCase.getInstallReferrer()?.installReferrer
        } else {
            referrer
        }
    }
}