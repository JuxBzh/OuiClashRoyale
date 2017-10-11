package com.jux.ouiclashroyale.data.source

import com.google.gson.Gson
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.remote.ArenasRemoteDataSource
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * The entry point for fetching arenas either form the network or a local cache
 */
class ArenasRepository(private val remoteDataSource: ArenasRemoteDataSource, private val gson: Gson) : ArenasDataSource {


    override fun getArena(id: String, callback: ArenasDataSource.ArenaCallback) {
    }

    override fun getArenas(callback: ArenasDataSource.ArenasCallback) {
        remoteDataSource.getArenas(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                parseResponse(response, callback)
            }

            override fun onFailure(call: Call?, e: IOException?) {
                callback.onError()
            }
        })
    }

    private fun parseResponse(response: Response?, callback: ArenasDataSource.ArenasCallback) {
        if (response == null || !response.isSuccessful) {
            callback.onError()
            return
        }

        try {
            val arenas = gson.fromJson<Array<Arena>>(response.body()?.charStream(), Array<Arena>::class.java)
            callback.onArenasLoaded(arenas)
        } catch (exception: Exception) {
            callback.onError()
        } finally {
            response.body()?.close()
        }
    }
}