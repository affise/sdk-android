package com.affise.attribution.referrer

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

fun Context.getPartnerKey(): String? {
    return try {
        //Create InputStream
        this.assets.open("partner_key").use { inputStream ->
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
}