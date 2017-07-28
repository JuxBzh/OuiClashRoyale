package com.jux.ouiclashroyale.ui.home

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.model.Arena
import com.jux.ouiclashroyale.network.HttpClient
import com.jux.ouiclashroyale.network.HttpClientCallback
import com.jux.ouiclashroyale.ui.arena.ArenaActivity
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.Call
import okhttp3.Request
import java.io.IOException


class HomeActivity : AppCompatActivity(),
        SwipeRefreshLayout.OnRefreshListener,
        ArenaAdapter.OnItemClickListener {

    val tag: String = "HomeActivity"
    val arenasUrl: String = "http://www.clashapi.xyz/api/arenas"

    val adapter: ArenaAdapter = ArenaAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        setupSwipeRefreshLayout()
        setupRecyclerView()
        onRefresh()
    }

    // SwipeRefreshLayout.OnRefreshListener
    override fun onRefresh() {
        swipe_refresh_layout.isRefreshing = true

        // Download arenas
        val request: Request = Request.Builder().url(arenasUrl).build()
        HttpClient.asynchronousCall(request, object : HttpClientCallback<Array<Arena>> {
            override fun onSuccess(result: Array<Arena>) {
                Log.i(tag, "Number of arenas: " + result.size)

                runOnUiThread({ refreshSucceeded(result) })
            }

            override fun onFailure(call: Call?, e: IOException?) {
                runOnUiThread({ refreshFailed() })
            }
        }, Array<Arena>::class.java)
    }

    // ArenaAdapter.OnItemClickListener
    override fun onItemClicked(position: Int, arenaIcon: ImageView) {
        val arena = adapter.getItem(position)

        val transitionName = ViewCompat.getTransitionName(arenaIcon)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                arenaIcon, transitionName)

        val intent = ArenaActivity.getIntent(this, arena.id, transitionName)
        startActivity(intent, options.toBundle())
    }

    // Helper methods

    private fun setupSwipeRefreshLayout() {
        swipe_refresh_layout.setOnRefreshListener(this)
        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
    }

    private fun setupRecyclerView() {
        adapter.setOnItemClickListener(this)
        adapter.setHasStableIds(true)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun refreshSucceeded(arenas: Array<Arena>) {
        // Hide refresh indicator
        swipe_refresh_layout.isRefreshing = false

        // Make the list of arenas visible
        empty.visibility = View.GONE
        list.visibility = View.VISIBLE

        // Set adapter data
        val list = mutableListOf<Arena>()
        list.addAll(arenas)
        adapter.setData(list)
    }

    private fun refreshFailed() {
        // Hide refresh indicator
        swipe_refresh_layout.isRefreshing = false

        // Make "empty" view visible
        empty.visibility = View.VISIBLE
        list.visibility = View.GONE
    }
}