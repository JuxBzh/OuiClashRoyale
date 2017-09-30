package com.jux.ouiclashroyale.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.ArenasDataSource


class ArenasViewModel : ViewModel(), ArenasDataSource.ArenasCallback {
    private var arenas: MutableLiveData<Array<Arena>>? = null
    private val error: MutableLiveData<String> = MutableLiveData()

    private var dataSource: ArenasDataSource? = null

    fun getArenas(): LiveData<Array<Arena>>? {
        if (arenas == null) {
            arenas = MutableLiveData()
            dataSource?.getArenas(this)
        }

        return arenas
    }

    fun setDataSource(dataSource: ArenasDataSource) {
        this.dataSource = dataSource
    }

    // ArenasDataSource.ArenasCallback

    override fun onArenasLoaded(arenas: Array<Arena>) {
        this.arenas?.postValue(arenas)
        error.postValue("")
    }

    override fun onError() {
        error.postValue("Couldn't load arenas")
    }
}