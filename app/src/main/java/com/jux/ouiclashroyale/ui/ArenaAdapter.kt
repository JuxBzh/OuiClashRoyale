package com.jux.ouiclashroyale.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.data.Arena

/**
 * An adapter displaying a list of arenas
 */
class ArenaAdapter : RecyclerView.Adapter<ArenaViewHolder>() {

    private val data: MutableList<Arena> = mutableListOf()

    override fun onBindViewHolder(holder: ArenaViewHolder?, position: Int) {
        val arena = data[position]
        holder?.name?.text = "Name"
        holder?.trophies?.text = "5"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ArenaViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_arena, parent, false)
        return ArenaViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun swapData(arenas: MutableList<Arena>) {
        data.clear()
        data.addAll(arenas.toList())
        notifyDataSetChanged()
    }
}