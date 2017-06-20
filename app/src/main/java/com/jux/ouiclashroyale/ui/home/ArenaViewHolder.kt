package com.jux.ouiclashroyale.ui.home

import android.view.View
import android.widget.TextView
import com.jux.recyclerviewtoolkit.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_arena.view.*

class ArenaViewHolder(itemView: View, listener: OnItemClickListener) : BaseViewHolder(itemView, listener) {
    val name: TextView = itemView.name
}