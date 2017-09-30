package com.jux.ouiclashroyale.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item_arena.view.*


class ArenaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val logo: ImageView = itemView.logo
    val name: TextView = itemView.name
    val trophies: TextView = itemView.trophies
}