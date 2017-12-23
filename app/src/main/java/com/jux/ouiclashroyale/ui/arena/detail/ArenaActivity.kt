package com.jux.ouiclashroyale.ui.arena.detail

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.data.source.ArenasRepository
import com.jux.ouiclashroyale.data.source.model.RemoteArenaMapper
import com.jux.ouiclashroyale.data.source.remote.ArenasRemoteDataSource
import com.jux.ouiclashroyale.ui.common.ArenaImageLoader
import com.jux.ouiclashroyale.ui.common.livedata.SnackbarMessage
import com.jux.ouiclashroyale.viewmodel.ArenaViewModel
import kotlinx.android.synthetic.main.activity_arena.*
import okhttp3.OkHttpClient


class ArenaActivity : AppCompatActivity(),
        SnackbarMessage.SnackbarObserver {

    companion object {
        private const val EXTRA_ARENA_ID = "extra_arena_id"

        fun getIntent(context: Context, arenaId: String): Intent {
            val intent = Intent(context, ArenaActivity::class.java)
            intent.putExtra(EXTRA_ARENA_ID, arenaId)
            return intent
        }
    }

    private lateinit var arenaImageLoader: ArenaImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arena)

        setupView()
        setupViewModel()
    }

    private fun setupView() {
        arenaImageLoader = ArenaImageLoader(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        val arenaId = intent.getStringExtra(EXTRA_ARENA_ID)

        val remoteDataSource = ArenasRemoteDataSource(OkHttpClient())
        val mapper = RemoteArenaMapper()
        val repository = ArenasRepository(remoteDataSource, mapper, GsonBuilder().create())

        val viewModel = ArenaViewModel(arenaId)
        viewModel.setDataSource(repository)

        viewModel.getArena()?.observe(this, Observer {
            val arena = it ?: return@Observer

            collapsingToolbarLayout.title = arena.name
            arenaImageLoader.load(arena.idName, arena_logo)

            minTrophies.setText(arena.minTrophies.toString())
            goldPerVictory.setText(arena.goldPerVictory.toString())
        })

        viewModel.error.observe(this, this)
    }

    // SnackbarObserver
    override fun onNewMessage(message: String) {
        Snackbar.make(coordinator, message, Snackbar.LENGTH_SHORT)
    }
}