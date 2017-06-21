package com.jux.ouiclashroyale.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.model.Arena
import com.jux.recyclerviewtoolkit.adapter.BaseAdapter
import com.jux.recyclerviewtoolkit.viewholder.BaseViewHolder


class ArenaAdapter(context: Context) : BaseAdapter<Arena>(context) {
    val imageLoader: RequestManager by lazy { Glide.with(mContext) }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_arena, parent, false)
        return ArenaViewHolder(itemView, this)
    }

    override fun onBindBaseViewHolder(baseViewHolder: BaseViewHolder?, i: Int) {
        val viewHolder: ArenaViewHolder = baseViewHolder as ArenaViewHolder
        val arena: Arena = getItem(i)

        viewHolder.name.text = arena.name
        viewHolder.trophies.text = mContext.getString(R.string.min_trophies, arena.minTrophies)

        imageLoader
                .load("http://www.clashapi.xyz/images/arenas/${arena.idName}.png")
                .asBitmap()
                .centerCrop()
                .into(viewHolder.logo)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).number.toLong()
    }
}