package com.jux.ouiclashroyale.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.Card
import com.jux.ouiclashroyale.data.source.dataSource.ArenasDataSource
import com.jux.ouiclashroyale.data.source.dataSource.CardsDataSource
import com.jux.ouiclashroyale.ui.common.livedata.SnackbarMessage


class ArenaViewModel(private val arenaId: String) : ViewModel(), ArenasDataSource.ArenaCallback,
        CardsDataSource.CardCallback {

    private var arena: MutableLiveData<Arena>? = null
    private var cards = MutableLiveData<MutableList<Card>>()

    private var arenasDataSource: ArenasDataSource? = null
    private var cardsDataSource: CardsDataSource? = null

    val error: SnackbarMessage = SnackbarMessage()

    fun getArena(): LiveData<Arena>? {
        if (arena == null) {
            arena = MutableLiveData()
            fetchArena()
        }
        return arena
    }

    fun getCards(): LiveData<MutableList<Card>> = cards

    fun setArenasDataSource(dataSource: ArenasDataSource?) {
        arenasDataSource = dataSource
    }

    fun setCardsDataSource(dataSource: CardsDataSource?) {
        cardsDataSource = dataSource
    }

    private fun fetchArena() {
        arenasDataSource?.getArena(arenaId, this)
    }

    fun fetchCard(id: String) {
        cardsDataSource?.getCard(id, this)
    }

    // ArenasDataSource.ArenaCallback
    override fun onArenaLoaded(arena: Arena) {
        this.arena?.postValue(arena)
    }

    override fun onError() {
        error.postValue("Loading failed!!")
    }

    // CardsDataSource.CardCallback
    override fun onCardLoaded(card: Card) {
        // NB: Must use LiveData.postValue() when running on a background thread
        val value = cards.value ?: mutableListOf()
        value.add(card)
        cards.postValue(value)
    }
}