package com.jux.ouiclashroyale.old.ui.arena

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.old.model.Arena
import com.jux.ouiclashroyale.old.network.HttpClient
import com.jux.ouiclashroyale.old.network.HttpClientCallback
import kotlinx.android.synthetic.main.activity_arena.*
import okhttp3.Call
import okhttp3.Request
import java.io.IOException
import java.lang.Exception

class ArenaActivity : AppCompatActivity() {
    private val arenaUrl = "http://www.clashapi.xyz/api/arenas/"
    private val imageLoader: RequestManager by lazy { Glide.with(this) }

    private val requestListener = object : RequestListener<String, Bitmap> {
        override fun onResourceReady(resource: Bitmap?, model: String?, target: Target<Bitmap>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
            // Start the Shared Transition Animation
            supportStartPostponedEnterTransition()
            return false
        }

        override fun onException(e: Exception?, model: String?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
            // Start the Shared Transition Animation
            supportStartPostponedEnterTransition()
            return false
        }

    }

    companion object {
        private val EXTRA_ARENA_ID: String = "extra_arena_activity"
        private val EXTRA_TRANSITION_NAME: String = "extra_transition_name"

        fun getIntent(context: Context, arenaId: String, transitionName: String?): Intent {
            val intent = Intent(context, ArenaActivity::class.java)
            intent.putExtra(EXTRA_ARENA_ID, arenaId)
            if (transitionName != null) intent.putExtra(EXTRA_TRANSITION_NAME, transitionName)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arena)

        // Wait until the arena logo is loaded to play the Shared Transition Animation
        supportPostponeEnterTransition()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""

        if (intent.hasExtra(EXTRA_TRANSITION_NAME)) {
            val transitionName = intent.getStringExtra(EXTRA_TRANSITION_NAME)
            ViewCompat.setTransitionName(arena_logo, transitionName)
        }

        downloadArena()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                // Reverse the Shared Transition Animation when navigating UP
                supportFinishAfterTransition()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun onArenaDownloaded(arena: Arena) {
        title = arena.name
        imageLoader
                .load("http://www.clashapi.xyz/images/arenas/${arena.idName}.png")
                .asBitmap()
                .listener(requestListener)
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
        val arenaId: String = intent.getStringExtra(EXTRA_ARENA_ID)
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