package com.jux.ouiclashroyale.data.source

import com.google.gson.Gson
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.remote.ArenasRemoteDataSource
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
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
                if (response?.isSuccessful == true) {
                    callback.onArenasLoaded(parseArenas(response.body()))
                } else {
                    callback.onError()
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                callback.onError()
            }
        })
    }

    private fun parseArenas(body: ResponseBody?): Array<Arena> {
        return try {
            gson.fromJson<Array<Arena>>(body?.charStream(), Array<Arena>::class.java)
        } catch (exception: Exception) {
            emptyArray()
        }
    }
}