package com.jux.ouiclashroyale.data.source.repository

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.jux.ouiclashroyale.data.Card
import com.jux.ouiclashroyale.data.source.dataSource.CardsDataSource
import com.jux.ouiclashroyale.data.source.mapper.RemoteCardMapper
import com.jux.ouiclashroyale.data.source.model.RemoteCard
import com.jux.ouiclashroyale.data.source.remote.CardsRemoteDataSource
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.io.IOException
import java.io.Reader

class CardsRepositoryTest {
    private fun <T> any(): T {
        Mockito.any<T>()
        return anyNotNull()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> anyNotNull(): T = null as T

    @Mock private val remoteDataSource = mock(CardsRemoteDataSource::class.java)
    @Mock private val mapper = mock(RemoteCardMapper::class.java)
    @Mock private val gson = mock(Gson::class.java)

    private fun setupRepository() = CardsRepository(remoteDataSource, mapper, gson)

    @Test
    fun getCardOnFailure() {
        // GIVEN
        val call = mock(Call::class.java)
        val exception = mock(IOException::class.java)
        doAnswer { invocation ->
            val arg = invocation!!.arguments[1] as Callback
            arg.onFailure(call, exception)
        }.`when`(remoteDataSource).getCard(any(), any())

        val repository = setupRepository()

        // WHEN
        val cardCallback = mock(CardsDataSource.CardCallback::class.java)
        repository.getCard("id", cardCallback)

        // THEN
        verify(cardCallback).onError()
    }

    @Test
    fun getCardOnSuccessWithUnsuccessfulResponse() {
        // GIVEN
        val call = mock(Call::class.java)
        val response = mock(Response::class.java)

        `when`(response.isSuccessful).thenReturn(false)
        doAnswer { invocation ->
            val arg = invocation!!.arguments[1] as Callback
            arg.onResponse(call, response)
        }.`when`(remoteDataSource).getCard(any(), any())

        val repository = setupRepository()

        // WHEN
        val cardCallback = mock(CardsDataSource.CardCallback::class.java)
        repository.getCard("id", cardCallback)

        // THEN
        verify(cardCallback).onError()
    }

    @Test
    fun getCardOnSuccessWithSuccessfulResponse() {
        // GIVEN
        val call = mock(Call::class.java)
        val response = mock(Response::class.java)
        val body = mock(ResponseBody::class.java)
        val reader = mock(Reader::class.java)
        val remoteCard = RemoteCard()
        val card = Card()

        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(body)
        `when`(body.charStream()).thenReturn(reader)
        `when`(gson.fromJson<RemoteCard>(body?.charStream(), RemoteCard::class.java)).thenReturn(remoteCard)
        `when`(mapper.map(remoteCard)).thenReturn(card)

        doAnswer { invocation ->
            val arg = invocation!!.arguments[1] as Callback
            arg.onResponse(call, response)
        }.`when`(remoteDataSource).getCard(any(), any())

        val repository = setupRepository()

        // WHEN
        val cardCallback = mock(CardsDataSource.CardCallback::class.java)
        repository.getCard("id", cardCallback)

        // THEN
        verify(cardCallback).onCardLoaded(card)
        verify(cardCallback, times(0)).onError()
    }

    @Test
    fun getCardOnSuccessWithSuccessfulResponseButParsingError() {
        // GIVEN
        val call = mock(Call::class.java)
        val response = mock(Response::class.java)
        val body = mock(ResponseBody::class.java)
        val reader = mock(Reader::class.java)

        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(body)
        `when`(body.charStream()).thenReturn(reader)
        `when`(gson.fromJson<RemoteCard>(body?.charStream(), RemoteCard::class.java)).thenThrow(JsonParseException(""))

        doAnswer { invocation ->
            val arg = invocation!!.arguments[1] as Callback
            arg.onResponse(call, response)
        }.`when`(remoteDataSource).getCard(any(), any())

        val repository = setupRepository()

        // WHEN
        val cardCallback = mock(CardsDataSource.CardCallback::class.java)
        repository.getCard("id", cardCallback)

        // THEN
        verify(cardCallback).onError()
    }
}