package com.jux.ouiclashroyale.network

import okhttp3.Call
import java.io.IOException

/**
 * Callback interface to get notified when an HTTP request finishes
 */
interface HttpClientCallback<R> {
    fun onSuccess(result: R)
    fun onFailure(call: Call?, e: IOException?)
}