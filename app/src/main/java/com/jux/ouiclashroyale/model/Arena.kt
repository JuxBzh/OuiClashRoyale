package com.jux.ouiclashroyale.model

import com.google.gson.annotations.SerializedName

/**
 * Represents an arena
 */
data class Arena(
        @SerializedName("_id") val id: String = "",
        @SerializedName("idName") val idName: String = ""
)
