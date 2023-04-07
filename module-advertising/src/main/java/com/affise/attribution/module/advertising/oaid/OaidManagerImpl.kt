package com.affise.attribution.module.advertising.oaid

import android.content.Context
import com.affise.attribution.build.BuildConfigPropertiesProvider
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.logs.LogsManager
import com.affise.attribution.module.advertising.oaid.hms.OpenDeviceIdentifierClient
import com.affise.attribution.module.advertising.oaid.hms.OpenDeviceIdentifierConnector
import com.bun.miitmdid.core.MdidSdkHelper
import java.io.BufferedReader
import java.io.InputStreamReader

internal class OaidManagerImpl(
    private val logsManager: LogsManager,
    private val executorServiceProvider: ExecutorServiceProvider,
    private val propertiesProvider: BuildConfigPropertiesProvider
) : OaidManager {

    /**
     * is Available Msa Sdk
     */
    private var oaid: String? = null

    override fun init(app: Context) {
        executorServiceProvider.provideExecutorService().execute {
            oaid = getOaid(app)
        }
    }

    /**
     * Get OAID
     */
    override fun getOaid() = oaid

    /**
     * Get OAID with [context]
     */
    private fun getOaid(context: Context): String? {
        //Create OpenDeviceIdentifierConnector
        val connector = OpenDeviceIdentifierConnector(context)

        //Check is Manufacturer on Huawei
        if (isManufacturerHuawei()) {
            //Create first attempt
            var attempt = 1

            //Use three attempts
            while (attempt <= 3) {
                //Create OpenDeviceIdentifierClient
                OpenDeviceIdentifierClient(context, (3000 * attempt).toLong(), connector)
                    //Get OAID
                    .getOaidInfo()
                    ?.let {
                        //If OAID is not null return it
                        return it
                    }

                //Update attempt
                attempt += 1
            }
        } else {
            //Check Available Sdk
            try {
                //loadLibrary
                System.loadLibrary(LIBRARY_NAME)

                //Read Certificate
                readCertFromAssetFile(context)?.let { certificate ->
                    try {
                        //Init Certificate
                        MdidSdkHelper.InitCert(context, certificate)

                        //Init Sdk
                        MdidSdkHelper.InitSdk(context, true) { idSupplier ->
                            //Set OAID result
                            oaid = idSupplier?.oaid
                        }
                    } catch (throwable: Throwable) {
                        //Log error
                        logsManager.addDeviceError(ERROR_INTEGRATION)
                    }
                }
            } catch (throwable: Throwable) {
                //Log error
                logsManager.addDeviceError(ERROR_INTEGRATION)
            }
        }
        return null
    }

    /**
     * Check manufacturer huawei
     *
     * @return manufacturer huawei or not
     */
    private fun isManufacturerHuawei(): Boolean = try {
        //Get manufacturer
        val manufacturer = propertiesProvider.getManufacturer()

        //Check manufacturer
        manufacturer != null && manufacturer.equals("huawei", ignoreCase = true)
    } catch (e: Exception) {
        false
    }

    /**
     * Read Certificate in assert file. For access need [context]
     * @return String data certificate or null
     */
    private fun readCertFromAssetFile(context: Context): String? = try {
        //Create InputStream
        context.assets.open(CERT_FILE_NAME).use { inputStream ->
            //Create BufferedReader
            BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
                //Create StringBuilder
                val builder = StringBuilder()
                //Create temp line
                var line: String?

                //Read file
                do {
                    line = bufferedReader.readLine()
                    builder.append("$line\n")
                } while (line != null)

                //Crete String result
                builder.toString()
            }
        }
    } catch (throwable: Throwable) {
        //Log error
        logsManager.addDeviceError(throwable)

        null
    }

    companion object {
        private const val LIBRARY_NAME = "nllvm1623827671"
        private const val CERT_FILE_NAME = "oaid.cert.pem"
        private const val ERROR_INTEGRATION = "Library OAID not integration in project"
    }
}