package com.affise.attribution.utils

import com.affise.attribution.network.HttpResponse
import javax.net.ssl.HttpsURLConnection

fun isHttpValid(responseCode: Int): Boolean {
    return responseCode in HttpsURLConnection.HTTP_OK until HttpsURLConnection.HTTP_BAD_REQUEST
}

fun isRedirect(responseCode: Int): Boolean {
    return responseCode in HttpsURLConnection.HTTP_MULT_CHOICE until HttpsURLConnection.HTTP_BAD_REQUEST
}

fun HttpResponse.isHttpValid(): Boolean = isHttpValid(this.code)

fun HttpResponse.isRedirect(): Boolean = isRedirect(this.code)
