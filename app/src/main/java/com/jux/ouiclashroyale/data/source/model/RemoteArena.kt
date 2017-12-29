package com.jux.ouiclashroyale.data.source.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Represents an arena returned by the remote data source
 */
data class RemoteArena(
        @SerializedName("_id") val id: String = "",
        val idName: String = "",
        val name: String = "name",
        val minTrophies: Int = 0,
        val victoryGold: Int = 0,
        val cardUnlocks: Array<String> = arrayOf()) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RemoteArena

        if (id != other.id) return false
        if (idName != other.idName) return false
        if (name != other.name) return false
        if (minTrophies != other.minTrophies) return false
        if (victoryGold != other.victoryGold) return false
        if (!Arrays.equals(cardUnlocks, other.cardUnlocks)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + idName.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + minTrophies
        result = 31 * result + victoryGold
        result = 31 * result + Arrays.hashCode(cardUnlocks)
        return result
    }
}