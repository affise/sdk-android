package com.affise.attribution.network

import com.affise.attribution.network.entity.PostBackModel

/**
 * Cloud repository interface
 */
interface CloudRepository {

    /**
     * Send [data] to current [url]
     */
    fun send(data: List<PostBackModel>, url: String)
}