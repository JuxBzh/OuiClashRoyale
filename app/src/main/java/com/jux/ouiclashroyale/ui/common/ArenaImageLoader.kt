package com.jux.ouiclashroyale.ui.common

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Component for loading arena images
 */
class ArenaImageLoader(activity: Activity) {
    private val imageUrl = "http://www.clashapi.xyz/images/arenas/%s.png"

    private val glide = Glide.with(activity)

    /**
     * Load the image of the arena into the ImageView
     */
    fun load(idName: String, imageView: ImageView?) {
        if (imageView == null) return

        val url = imageUrl.format(idName)
        glide.load(url).asBitmap().into(imageView)
    }
}