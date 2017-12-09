package com.jux.ouiclashroyale.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.view.View
import android.widget.ImageView
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.ArenasDataSource
import com.jux.ouiclashroyale.ui.common.livedata.SingleLiveEvent


class ArenasViewModel : ViewModel(), ArenasDataSource.ArenasCallback {
    private var arenas: MutableLiveData<Array<Arena>>? = null

    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val error: SingleLiveEvent<String> = SingleLiveEvent()

    val listViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val emptyViewVisibility: MutableLiveData<Int> = MutableLiveData()

    val arenaClicked: SingleLiveEvent<String> = SingleLiveEvent()

    private var dataSource: ArenasDataSource? = null

    fun getArenas(): LiveData<Array<Arena>>? {
        if (arenas == null) {
            arenas = MutableLiveData()
            fetchArenas()
        }
        return arenas
    }

    fun refreshArenas() {
        fetchArenas()
    }

    fun onArenaSelected(arena: Arena, arenaImage: ImageView) {
        arenaClicked.setValue(arena.id)
    }

    fun setDataSource(dataSource: ArenasDataSource) {
        this.dataSource = dataSource
    }

    private fun fetchArenas() {
        loading.postValue(true)
        dataSource?.getArenas(this)
    }

    // ArenasDataSource.ArenasCallback

    override fun onArenasLoaded(arenas: Array<Arena>) {
        this.arenas?.postValue(arenas)

        // Not loading anymore
        loading.postValue(false)

        // Set List and Empty view visibility
        listViewVisibility.postValue(if (arenas.isEmpty()) View.GONE else View.VISIBLE)
        emptyViewVisibility.postValue(if (arenas.isEmpty()) View.VISIBLE else View.GONE)
    }

    override fun onError() {
        // Not loading anymore
        loading.postValue(false)

        // Set List and Empty view visibility
        listViewVisibility.postValue(View.GONE)
        emptyViewVisibility.postValue(View.VISIBLE)

        // Error occurred
        error.postValue("Couldn't load arenas")
    }
}