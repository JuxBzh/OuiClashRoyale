package com.jux.ouiclashroyale.data.source.model

import com.google.gson.annotations.SerializedName

/**
 * Represents an arena returned by the remote data source
 */
data class RemoteArena(
        @SerializedName("_id") val id: String = "",
        val idName: String = "",
        val name: String = "name",
        val minTrophies: Int = 0)