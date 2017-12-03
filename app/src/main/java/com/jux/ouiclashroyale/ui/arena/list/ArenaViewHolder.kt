package com.jux.ouiclashroyale.ui.arena.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item_arena.view.*


class ArenaViewHolder(itemView: View, private val listener: OnClickListener) : RecyclerView.ViewHolder(itemView) {
    val logo: ImageView = itemView.logo
    val name: TextView = itemView.name
    val trophies: TextView = itemView.trophies

    init {
        itemView.setOnClickListener {
            listener.onClick(this, adapterPosition)
        }
    }

    interface OnClickListener {
        fun onClick(holder: ArenaViewHolder, position: Int)
    }
}