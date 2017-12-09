package com.jux.ouiclashroyale.ui.arena.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jux.ouiclashroyale.R


class ArenaActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ARENA_ID = "extra_arena_id"

        fun getIntent(context: Context, arenaId: String): Intent {
            val intent = Intent(context, ArenaActivity::class.java)
            intent.putExtra(EXTRA_ARENA_ID, arenaId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arena)
    }
}