package com.jux.ouiclashroyale.ui.card

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.data.Card
import com.jux.ouiclashroyale.ui.common.ImageLoader


class CardAdapter(private val imageLoader: ImageLoader) : RecyclerView.Adapter<CardViewHolder>() {
    private var data: MutableList<Card> = mutableListOf()

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = data[position]
        imageLoader.loadCardImage(card.idName, holder.image)
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_card, parent, false)
        return CardViewHolder(itemView)
    }

    fun swapData(cards: List<Card>?) {
        data.clear()
        if (cards != null) data.addAll(cards)
        notifyDataSetChanged()
    }
}