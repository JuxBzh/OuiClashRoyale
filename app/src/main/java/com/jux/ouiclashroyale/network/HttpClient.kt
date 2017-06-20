package com.jux.ouiclashroyale.network

import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

/**
 * HTTP client implemented as a Singleton
 */
object HttpClient {
    private val tag: String = "HttpClient"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

    /**
     * Perform an asynchronous HTTP request
     *
     * @param request The object representing the request
     * @param callback The callback to get notified the HTTP response is returned
     */
    fun <R> asynchronousCall(request: Request, callback: HttpClientCallback<R>, clazz: Class<R>) {
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                if (response?.isSuccessful as Boolean) {
                    val result = Gson().fromJson<R>(response.body()?.charStream(), clazz)
                    callback.onSuccess(result)
                } else {
                    callback.onFailure(call, IOException("Unable to read response"))
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                Log.e(tag, "HTTP request failed. URL: " + call?.request()?.url(), e)
                callback.onFailure(call, e)
            }
        })
    }
}