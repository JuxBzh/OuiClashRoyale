package com.jux.ouiclashroyale.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.model.Arena
import com.jux.ouiclashroyale.network.HttpClient
import com.jux.ouiclashroyale.network.HttpClientCallback
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.Call
import okhttp3.Request
import java.io.IOException


class HomeActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    val tag: String = "HomeActivity"
    val arenaUrl: String = "http://www.clashapi.xyz/api/arenas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        setupSwipeRefreshLayout()
        onRefresh()
    }

    // SwipeRefreshLayout.OnRefreshListener
    override fun onRefresh() {
        swipe_refresh_layout.isRefreshing = true

        // Download arenas
        val request: Request = Request.Builder().url(arenaUrl).build()
        HttpClient.asynchronousCall(request, object : HttpClientCallback<Array<Arena>> {
            override fun onSuccess(result: Array<Arena>) {
                Log.i(tag, "Number of arenas: " + result.size)

                Handler(Looper.getMainLooper()).post({
                    refreshSucceeded(result)
                })
            }

            override fun onFailure(call: Call?, e: IOException?) {
                Handler(Looper.getMainLooper()).post({
                    refreshFailed()
                })
            }
        }, Array<Arena>::class.java)
    }

    private fun setupSwipeRefreshLayout() {
        swipe_refresh_layout.setOnRefreshListener(this)
        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
    }

    private fun refreshSucceeded(arenas: Array<Arena>) {
        // Hide refresh indicator
        swipe_refresh_layout.isRefreshing = false

        // Make the list of arenas visible
        empty.visibility = View.GONE
        list.visibility = View.VISIBLE
    }

    private fun refreshFailed() {
        // Hide refresh indicator
        swipe_refresh_layout.isRefreshing = false

        // Make "empty" view visible
        empty.visibility = View.VISIBLE
        list.visibility = View.GONE
    }
}