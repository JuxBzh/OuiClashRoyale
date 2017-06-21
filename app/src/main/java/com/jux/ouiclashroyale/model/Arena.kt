package com.jux.ouiclashroyale.model

import com.google.gson.annotations.SerializedName

/**
 * Represents an arena
 */
data class Arena(
        @SerializedName("_id") val id: String = "",
        @SerializedName("idName") val idName: String = "",
        @SerializedName("number") val number: Int = 0,
        @SerializedName("name") val name: String = "",
        @SerializedName("victoryGold") val victoryGold: Int = 0,
        @SerializedName("minTrophies") val minTrophies: Int = 0,
        @SerializedName("order") val order: Int = 0,
        @SerializedName("cardUnlocks") val cardUnlocks: Array<String> = arrayOf()
)
