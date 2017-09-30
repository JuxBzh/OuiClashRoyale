package com.jux.ouiclashroyale.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.ArenasRepository
import com.jux.ouiclashroyale.data.source.remote.ArenasRemoteDataSource
import com.jux.ouiclashroyale.viewmodel.ArenasViewModel
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.OkHttpClient


class ArenasActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private val adapter = ArenaAdapter()
    private lateinit var viewModel: ArenasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupView()
        setupViewModel()
    }

    private fun setupView() {
        setSupportActionBar(toolbar)

        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
        swipe_refresh_layout.setOnRefreshListener(this)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun setupViewModel() {
        val httpClient = OkHttpClient.Builder().build()

        val remoteDataSource = ArenasRemoteDataSource(httpClient)
        val repository = ArenasRepository(remoteDataSource, GsonBuilder().create())

        viewModel = ViewModelProviders.of(this).get(ArenasViewModel::class.java)
        viewModel.setDataSource(repository)

        viewModel.getArenas()?.observe(this, Observer {
            val arenas = mutableListOf<Arena>()
            it?.forEach {
                arenas.add(it)
            }
            adapter.swapData(arenas)
        })
    }

    // SwipeRefreshLayout.OnRefreshListener
    override fun onRefresh() {
    }
}