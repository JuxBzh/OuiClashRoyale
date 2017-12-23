package com.jux.ouiclashroyale.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.ArenasDataSource
import com.jux.ouiclashroyale.ui.common.livedata.SnackbarMessage


class ArenaViewModel(private val arenaId: String) : ViewModel(), ArenasDataSource.ArenaCallback {
    private var arena: MutableLiveData<Arena>? = null
    private var dataSource: ArenasDataSource? = null

    val error: SnackbarMessage = SnackbarMessage()

    fun getArena(): LiveData<Arena>? {
        if (arena == null) {
            arena = MutableLiveData()
            fetchArena()
        }
        return arena
    }

    fun setDataSource(dataSource: ArenasDataSource?) {
        this.dataSource = dataSource
    }

    private fun fetchArena() {
        dataSource?.getArena(arenaId, this)
    }

    // ArenasDataSource.ArenaCallback
    override fun onArenaLoaded(arena: Arena) {
        this.arena?.postValue(arena)
    }

    override fun onError() {
        error.postValue("Failed to load arena!!")
    }
}