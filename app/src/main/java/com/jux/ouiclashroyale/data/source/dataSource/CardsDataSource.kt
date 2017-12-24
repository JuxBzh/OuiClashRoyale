package com.jux.ouiclashroyale.data.source.dataSource

import com.jux.ouiclashroyale.data.Card

/**
 * An interface defining how arenas can be accessed
 */
interface CardsDataSource {

    /**
     * A callback interface to get notified when an card is loaded
     */
    interface CardCallback {
        fun onCardLoaded(card: Card)
        fun onError()
    }

    /**
     * Fetch the card with the given ID
     */
    fun getCard(id: String, callback: CardCallback)
}