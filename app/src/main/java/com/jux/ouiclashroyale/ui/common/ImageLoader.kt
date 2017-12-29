package com.jux.ouiclashroyale.ui.common

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Component for loading images
 */
class ImageLoader(activity: Activity) {
    private val imageUrl = "http://www.clashapi.xyz/images/arenas/%s.png"
    private val cardUrl = "http://www.clashapi.xyz/images/cards/%s.png"

    private val glide = Glide.with(activity)

    /**
     * Load the image of the arena into the ImageView
     */
    fun loadArenaImage(idName: String, imageView: ImageView?) {
        if (imageView == null) return

        val url = imageUrl.format(idName)
        glide.load(url).asBitmap().into(imageView)
    }

    /**
     * Load the image of the card into the ImageView
     */
    fun loadCardImage(idName: String, imageView: ImageView?) {
        if (imageView == null) return

        val url = cardUrl.format(idName)
        glide.load(url).asBitmap().into(imageView)
    }
}