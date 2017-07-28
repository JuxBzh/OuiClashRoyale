package com.jux.ouiclashroyale.ui.arena

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.model.Arena
import com.jux.ouiclashroyale.network.HttpClient
import com.jux.ouiclashroyale.network.HttpClientCallback
import kotlinx.android.synthetic.main.activity_arena.*
import okhttp3.Call
import okhttp3.Request
import java.io.IOException

class ArenaActivity : AppCompatActivity() {
    val arenaUrl = "http://www.clashapi.xyz/api/arenas/"

    val imageLoader: RequestManager by lazy { Glide.with(this) }

    companion object {
        private val extraArenaId: String = "extra_arena_activity"

        fun getIntent(context: Context, arenaId: String): Intent {
            val intent = Intent(context, ArenaActivity::class.java)
            intent.putExtra(extraArenaId, arenaId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arena)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""

        downloadArena()
    }

    private fun onArenaDownloaded(arena: Arena) {
        title = arena.name
        imageLoader
                .load("http://www.clashapi.xyz/images/arenas/${arena.idName}.png")
                .asBitmap()
                .centerCrop()
                .into(arena_logo)

        setupDetailBlock(arena)
        setupCardsBlock(arena)
    }

    private fun setupDetailBlock(arena: Arena) {
        detail_victory_gold.text = getString(R.string.gold_per_victory, arena.victoryGold)
        detail_min_trophies.text = getString(R.string.min_trophies_long, arena.minTrophies)
    }

    private fun setupCardsBlock(arena: Arena) {
    }

    private fun downloadArena() {
        val arenaId: String = intent.getStringExtra(extraArenaId)
        val request: Request = Request.Builder().url(arenaUrl + arenaId).build()
        HttpClient.asynchronousCall(request, object : HttpClientCallback<Arena> {
            override fun onSuccess(result: Arena) {
                runOnUiThread({ onArenaDownloaded(result) })
            }

            override fun onFailure(call: Call?, e: IOException?) {
            }
        }, Arena::class.java)
    }
}