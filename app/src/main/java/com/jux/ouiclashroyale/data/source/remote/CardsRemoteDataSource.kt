package com.jux.ouiclashroyale.data.source.remote

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Represent the Data Source that will fetch cards from the network
 */
class CardsRemoteDataSource(private val httpClient: OkHttpClient) {

    private val baseUrl = "http://www.clashapi.xyz/api/cards/"

    fun getCard(id: String, callback: Callback) {
        val url = baseUrl + id
        val request = Request.Builder().url(url).build()

        httpClient.newCall(request).enqueue(callback)
    }
}