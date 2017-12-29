package com.jux.ouiclashroyale.data

import java.util.*

/**
 * Represents an arena
 */
data class Arena(
        val id: String = "",
        val idName: String = "",
        val name: String = "name",
        val minTrophies: Int = 0,
        val goldPerVictory: Int = 0,
        val cards: Array<String> = arrayOf()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Arena

        if (id != other.id) return false
        if (idName != other.idName) return false
        if (name != other.name) return false
        if (minTrophies != other.minTrophies) return false
        if (goldPerVictory != other.goldPerVictory) return false
        if (!Arrays.equals(cards, other.cards)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + idName.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + minTrophies
        result = 31 * result + goldPerVictory
        result = 31 * result + Arrays.hashCode(cards)
        return result
    }

}