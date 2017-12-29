package com.jux.ouiclashroyale.ui.card

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.jux.ouiclashroyale.R


class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.image)
}