package com.jux.ouiclashroyale.ui.arena.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.google.gson.GsonBuilder
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.mapper.RemoteArenaMapper
import com.jux.ouiclashroyale.data.source.remote.ArenasRemoteDataSource
import com.jux.ouiclashroyale.data.source.repository.ArenasRepository
import com.jux.ouiclashroyale.ui.arena.detail.ArenaActivity
import com.jux.ouiclashroyale.ui.common.ImageLoader
import com.jux.ouiclashroyale.ui.common.livedata.SnackbarMessage
import com.jux.ouiclashroyale.viewmodel.ArenasViewModel
import kotlinx.android.synthetic.main.activity_arenas.*
import okhttp3.OkHttpClient


class ArenasActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
        ArenaAdapter.OnItemClickListener,
        SnackbarMessage.SnackbarObserver {

    private lateinit var adapter: ArenaAdapter
    private lateinit var viewModel: ArenasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arenas)

        setupView()
        setupViewModel()
    }

    private fun setupView() {
        setSupportActionBar(toolbar)

        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh_layout.setOnRefreshListener(this)

        val imageLoader = ImageLoader(this)
        adapter = ArenaAdapter(imageLoader, this)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun setupViewModel() {
        val remoteDataSource = ArenasRemoteDataSource(OkHttpClient())
        val mapper = RemoteArenaMapper()
        val repository = ArenasRepository(remoteDataSource, mapper, GsonBuilder().create())

        viewModel = ViewModelProviders.of(this).get(ArenasViewModel::class.java)
        viewModel.setDataSource(repository)

        viewModel.getArenas()?.observe(this, Observer {
            val arenas = mutableListOf<Arena>()
            it?.forEach {
                arenas.add(it)
            }
            adapter.swapData(arenas)
        })

        viewModel.loading.observe(this, Observer {
            swipe_refresh_layout.isRefreshing = it ?: false
        })

        viewModel.listViewVisibility.observe(this, Observer {
            list.visibility = it ?: View.GONE
        })

        viewModel.emptyViewVisibility.observe(this, Observer {
            empty.visibility = it ?: View.GONE
        })

        viewModel.error.observe(this, this)

        viewModel.arenaClicked.observe(this, Observer {
            if (it != null) {
                val intent = ArenaActivity.getIntent(this, it)
                startActivity(intent)
            }
        })
    }

    // SwipeRefreshLayout.OnRefreshListener
    override fun onRefresh() {
        viewModel.refreshArenas()
    }

    // ArenaAdapter.OnItemClickListener
    override fun onItemClick(arena: Arena, imageView: ImageView) {
        viewModel.onArenaSelected(arena, imageView)
    }

    // SnackbarObserver
    override fun onNewMessage(message: String) {
        Snackbar.make(coordinator, message, Snackbar.LENGTH_SHORT).show()
    }
}