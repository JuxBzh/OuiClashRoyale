package com.jux.ouiclashroyale.data.source

import com.google.gson.Gson
import com.jux.ouiclashroyale.data.source.model.RemoteCard
import com.jux.ouiclashroyale.data.source.model.RemoteCardMapper
import com.jux.ouiclashroyale.data.source.remote.CardsRemoteDataSource
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * The entry point for fetching cards either form the network or a local cache
 */
class CardsRepository(private val remoteDataSource: CardsRemoteDataSource,
                      private val mapper: RemoteCardMapper,
                      private val gson: Gson) : CardsDataSource {

    override fun getCard(id: String, callback: CardsDataSource.CardCallback) {
        remoteDataSource.getCard(id, object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                parseCard(response, callback)
            }

            override fun onFailure(call: Call?, e: IOException?) {
                callback.onError()
            }
        })
    }

    private fun parseCard(response: Response?, callback: CardsDataSource.CardCallback) {
        if (response == null || !response.isSuccessful) {
            callback.onError()
            return
        }

        try {
            val card = gson.fromJson<RemoteCard>(response.body()?.charStream(), RemoteCard::class.java)
            callback.onCardLoaded(mapper.map(card))
        } catch (exception: Exception) {
            callback.onError()
        } finally {
            response.body()?.close()
        }
    }
}