package com.jux.ouiclashroyale.data.source.mapper

import com.jux.ouiclashroyale.data.Arena
import com.jux.ouiclashroyale.data.source.model.RemoteArena

/**
 * Utility class that maps a RemoteArena to an Arena
 */
class RemoteArenaMapper {
    fun map(remoteArenas: Array<RemoteArena>): Array<Arena> {
        return remoteArenas.map { map(it) }.toTypedArray()
    }

    fun map(remoteArena: RemoteArena): Arena {
        return Arena(remoteArena.id,
                remoteArena.idName,
                remoteArena.name,
                remoteArena.minTrophies,
                remoteArena.victoryGold,
                remoteArena.cardUnlocks)
    }
}