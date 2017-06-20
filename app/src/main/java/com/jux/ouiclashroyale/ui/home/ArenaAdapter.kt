package com.jux.ouiclashroyale.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jux.ouiclashroyale.R
import com.jux.ouiclashroyale.model.Arena
import com.jux.recyclerviewtoolkit.adapter.BaseAdapter
import com.jux.recyclerviewtoolkit.viewholder.BaseViewHolder


class ArenaAdapter(context: Context) : BaseAdapter<Arena>(context) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_arena, parent, false)
        return ArenaViewHolder(itemView, this)
    }

    override fun onBindBaseViewHolder(baseViewHolder: BaseViewHolder?, i: Int) {
        val viewHolder: ArenaViewHolder = baseViewHolder as ArenaViewHolder
        val arena: Arena = getItem(i)

        viewHolder.name.text = arena.name
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).number.toLong()
    }
}