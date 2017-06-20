package com.jux.ouiclashroyale.ui

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
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

    private fun setupSwipeRefreshLayout() {
        swipe_refresh_layout.setOnRefreshListener(this)
        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
    }

    // SwipeRefreshLayout.OnRefreshListener
    override fun onRefresh() {
        swipe_refresh_layout.isRefreshing = true

        // Perform API call
        val request: Request = Request.Builder().url(arenaUrl).build()
        HttpClient.asynchronousCall(request, object : HttpClientCallback<Array<Arena>> {
            override fun onSuccess(result: Array<Arena>) {
                Log.i(tag, "Number of arenas: " + result.size)
            }

            override fun onFailure(call: Call?, e: IOException?) {
            }
        }, Array<Arena>::class.java)
    }
}