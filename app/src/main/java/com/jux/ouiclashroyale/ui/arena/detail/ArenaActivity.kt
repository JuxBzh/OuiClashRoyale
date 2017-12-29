package com.jux.ouiclashroyale.ui.arena.detail

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.google.gson.GsonBuilder
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.mapper.RemoteArenaMapper
import com.jux.ouiclashroyale.data.source.mapper.RemoteCardMapper
import com.jux.ouiclashroyale.data.source.remote.ArenasRemoteDataSource
import com.jux.ouiclashroyale.data.source.remote.CardsRemoteDataSource
import com.jux.ouiclashroyale.data.source.repository.ArenasRepository
import com.jux.ouiclashroyale.data.source.repository.CardsRepository
import com.jux.ouiclashroyale.ui.card.CardAdapter
import com.jux.ouiclashroyale.ui.common.ImageLoader
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

    private lateinit var imageLoader: ImageLoader
    private lateinit var cardAdapter: CardAdapter

    private lateinit var viewModel: ArenaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arena)

        setupView()
        setupViewModel()
    }

    private fun setupView() {
        imageLoader = ImageLoader(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cardAdapter = CardAdapter(imageLoader)
        cards.isNestedScrollingEnabled = false
        cards.adapter = cardAdapter

        val cardsLayoutManager = GridLayoutManager(this, 3)
        cards.layoutManager = cardsLayoutManager
    }

    private fun setupViewModel() {
        val arenaId = intent.getStringExtra(EXTRA_ARENA_ID)

        val httpClient = OkHttpClient()
        val gson = GsonBuilder().create()

        val remoteArenaDataSource = ArenasRemoteDataSource(httpClient)
        val arenaMapper = RemoteArenaMapper()
        val arenaRepository = ArenasRepository(remoteArenaDataSource, arenaMapper, gson)

        val remoteCardsDataSource = CardsRemoteDataSource(httpClient)
        val cardMapper = RemoteCardMapper()
        val cardsRepository = CardsRepository(remoteCardsDataSource, cardMapper, gson)

        viewModel = ArenaViewModel(arenaId)
        viewModel.setArenasDataSource(arenaRepository)
        viewModel.setCardsDataSource(cardsRepository)

        viewModel.error.observe(this, this)

        viewModel.getArena()?.observe(this, Observer {
            val arena = it ?: return@Observer

            collapsingToolbarLayout.title = arena.name
            imageLoader.loadArenaImage(arena.idName, arena_logo)

            minTrophies.setText(arena.minTrophies.toString())
            goldPerVictory.setText(arena.goldPerVictory.toString())

            fetchCards(arena)
        })

        viewModel.getCards().observe(this, Observer {
            cardAdapter.swapData(it)
        })
    }

    private fun fetchCards(arena: Arena) {
        for (card in arena.cards) {
            viewModel.fetchCard(card)
        }
    }

    // SnackbarObserver
    override fun onNewMessage(message: String) {
        Snackbar.make(coordinator, message, Snackbar.LENGTH_SHORT).show()
    }
}