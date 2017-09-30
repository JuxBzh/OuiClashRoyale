package com.jux.ouiclashroyale.data

import com.google.gson.annotations.SerializedName

/**
 * Represent an arena
 */
data class Arena(
        @SerializedName("_id") val id: String = "",
        val idName: String = "",
        val name: String = "name",
        val minTrophies: Int = 0
)