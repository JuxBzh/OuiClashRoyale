package com.jux.ouiclashroyale.data.source.remote

import junit.framework.Assert
import okhttp3.*
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.io.IOException


class ArenasRemoteDataSourceTest {
    private fun <T> any(): T {
        Mockito.any<T>()
        return anyNotNull()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> anyNotNull(): T = null as T

    @Mock private val httpClient = mock(OkHttpClient::class.java)

    private fun setupDataSource(): ArenasRemoteDataSource = ArenasRemoteDataSource(httpClient)

    @Test
    fun getArena() {
        // GIVEN
        val callback = MockCallback()
        val call = mock(Call::class.java)

        `when`(httpClient.newCall(any())).thenReturn(call)

        // WHEN
        val dataSource = setupDataSource()
        dataSource.getArena("123", callback)

        // THEN
        val requestCaptor = ArgumentCaptor.forClass(Request::class.java)

        verify(httpClient).newCall(requestCaptor.capture())
        verify(call).enqueue(callback)

        val request = requestCaptor.value
        Assert.assertEquals("Wrong URL", "http://www.clashapi.xyz/api/arenas/123", request.url().toString())
    }

    @Test
    fun getArenas() {
        // GIVEN
        val callback = MockCallback()
        val call = mock(Call::class.java)

        `when`(httpClient.newCall(any())).thenReturn(call)

        // WHEN
        val dataSource = setupDataSource()
        dataSource.getArenas(callback)

        // THEN
        val requestCaptor = ArgumentCaptor.forClass(Request::class.java)

        verify(httpClient).newCall(requestCaptor.capture())
        verify(call).enqueue(callback)

        val request = requestCaptor.value
        Assert.assertEquals("Wrong URL", "http://www.clashapi.xyz/api/arenas", request.url().toString())
    }

    private class MockCallback : Callback {
        override fun onFailure(call: Call?, e: IOException?) {
        }

        override fun onResponse(call: Call?, response: Response?) {
        }
    }
}