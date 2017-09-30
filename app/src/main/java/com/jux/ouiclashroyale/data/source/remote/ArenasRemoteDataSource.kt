package com.jux.ouiclashroyale.data.source.remote

import okhttp3.OkHttpClient

/**
 * Represent the Data Source that will fetch arenas from the network
 */
class ArenasRemoteDataSource(private val httpClient: OkHttpClient) {

    private val BASE_URL = "http://www.clashapi.xyz/"

    fun getArena(id: String) {
    }
}