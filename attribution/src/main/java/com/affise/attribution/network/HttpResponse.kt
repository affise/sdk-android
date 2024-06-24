package com.affise.attribution.network


data class HttpResponse(
    val code: Int,
    val message: String,
    val body: String? = null,
    val headers: Map<String,List<String>> = emptyMap(),
)
