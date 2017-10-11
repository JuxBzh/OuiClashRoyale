package com.jux.ouiclashroyale.data.source

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.remote.ArenasRemoteDataSource
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

class ArenasRepositoryTest {
    private fun <T> any(): T {
        Mockito.any<T>()
        return anyNotNull()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> anyNotNull(): T = null as T

    @Mock private val remoteDataSource = mock(ArenasRemoteDataSource::class.java)
    @Mock private val gson = mock(Gson::class.java)

    @Test
    fun getArenasOnFailure() {
        // GIVEN
        val call = mock(Call::class.java)
        val exception = mock(IOException::class.java)
        doAnswer { invocation ->
            val arg = invocation!!.arguments[0] as Callback
            arg.onFailure(call, exception)
        }.`when`(remoteDataSource).getArenas(any())

        val repository = ArenasRepository(remoteDataSource, gson)

        // WHEN
        val arenasCallback = mock(ArenasDataSource.ArenasCallback::class.java)
        repository.getArenas(arenasCallback)

        // THEN
        verify(arenasCallback).onError()
    }

    @Test
    fun getArenasOnSuccessWithUnsuccessfulResponse() {
        // GIVEN
        val call = mock(Call::class.java)
        val response = mock(Response::class.java)

        `when`(response.isSuccessful).thenReturn(false)
        doAnswer { invocation ->
            val arg = invocation!!.arguments[0] as Callback
            arg.onResponse(call, response)
        }.`when`(remoteDataSource).getArenas(any())

        val repository = ArenasRepository(remoteDataSource, gson)

        // WHEN
        val arenasCallback = mock(ArenasDataSource.ArenasCallback::class.java)
        repository.getArenas(arenasCallback)

        // THEN
        verify(arenasCallback).onError()
    }

    @Test
    fun getArenasOnSuccessWithSuccessfulResponse() {
        // GIVEN
        val call = mock(Call::class.java)
        val response = mock(Response::class.java)
        val body = mock(ResponseBody::class.java)
        val reader = mock(Reader::class.java)
        val arenas = arrayOf<Arena>()

        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(body)
        `when`(body.charStream()).thenReturn(reader)
        `when`(gson.fromJson<Array<Arena>>(body?.charStream(), Array<Arena>::class.java)).thenReturn(arenas)

        doAnswer { invocation ->
            val arg = invocation!!.arguments[0] as Callback
            arg.onResponse(call, response)
        }.`when`(remoteDataSource).getArenas(any())

        val repository = ArenasRepository(remoteDataSource, gson)

        // WHEN
        val arenasCallback = mock(ArenasDataSource.ArenasCallback::class.java)
        repository.getArenas(arenasCallback)

        // THEN
        verify(arenasCallback).onArenasLoaded(arenas)
    }

    @Test
    fun getArenasOnSuccessWithSuccessfulResponseButParsingError() {
        // GIVEN
        val call = mock(Call::class.java)
        val response = mock(Response::class.java)
        val body = mock(ResponseBody::class.java)
        val reader = mock(Reader::class.java)

        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(body)
        `when`(body.charStream()).thenReturn(reader)
        `when`(gson.fromJson<Array<Arena>>(body?.charStream(), Array<Arena>::class.java)).thenThrow(JsonParseException(""))

        doAnswer { invocation ->
            val arg = invocation!!.arguments[0] as Callback
            arg.onResponse(call, response)
        }.`when`(remoteDataSource).getArenas(any())

        val repository = ArenasRepository(remoteDataSource, gson)

        // WHEN
        val arenasCallback = mock(ArenasDataSource.ArenasCallback::class.java)
        repository.getArenas(arenasCallback)

        // THEN
        verify(arenasCallback).onError()
    }
}