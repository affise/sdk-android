package com.affise.attribution.utils

import javax.net.ssl.HttpsURLConnection

fun isHttpValid(responseCode: Int): Boolean {
    return responseCode in HttpsURLConnection.HTTP_OK until HttpsURLConnection.HTTP_BAD_REQUEST
}