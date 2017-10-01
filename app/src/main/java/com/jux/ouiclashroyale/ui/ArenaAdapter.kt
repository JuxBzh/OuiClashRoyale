package com.jux.ouiclashroyale.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.data.Arena

/**
 * An adapter displaying a list of arenas
 */
class ArenaAdapter(private val imageLoader: ArenaImageLoader, private val listener: OnItemClickListener) :
        RecyclerView.Adapter<ArenaViewHolder>(),
        ArenaViewHolder.OnClickListener {

    private val data: MutableList<Arena> = mutableListOf()

    override fun onBindViewHolder(holder: ArenaViewHolder?, position: Int) {
        val arena = data[position]
        holder?.name?.text = arena.name
        holder?.trophies?.text = arena.minTrophies.toString()
        imageLoader.load(arena.idName, holder?.logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ArenaViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_arena, parent, false)
        return ArenaViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun swapData(arenas: MutableList<Arena>) {
        data.clear()
        data.addAll(arenas.toList())
        notifyDataSetChanged()
    }

    // ArenaViewHolder.OnClickListener
    override fun onClick(holder: ArenaViewHolder, position: Int) {
        listener.onItemClick(data[position], holder.logo)
    }

    /**
     * A callback interface to get notified when an item is clicked
     */
    interface OnItemClickListener {
        fun onItemClick(arena: Arena, imageView: ImageView)
    }
}