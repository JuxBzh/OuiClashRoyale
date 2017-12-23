package com.jux.ouiclashroyale.data.source.remote

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Represent the Data Source that will fetch arenas from the network
 */
class ArenasRemoteDataSource(private val httpClient: OkHttpClient) {

    private val baseUrl = "http://www.clashapi.xyz/"

    fun getArena(id: String, callback: Callback) {
        val url = baseUrl + "api/arenas/$id"
        val request = Request.Builder().url(url).build()

        httpClient.newCall(request).enqueue(callback)
    }

    fun getArenas(callback: Callback) {
        val url = baseUrl + "api/arenas"
        val request = Request.Builder().url(url).build()

        httpClient.newCall(request).enqueue(callback)
    }
}