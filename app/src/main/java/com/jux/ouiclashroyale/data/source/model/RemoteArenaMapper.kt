package com.jux.ouiclashroyale.data.source.model

import com.jux.ouiclashroyale.data.Arena

/**
 * Utility class that maps a RemoteArena to an Arena
 */
class RemoteArenaMapper {
    fun map(remoteArenas: Array<RemoteArena>): Array<Arena> {
        return remoteArenas.map { map(it) }.toTypedArray()
    }

    private fun map(remoteArena: RemoteArena): Arena {
        return Arena(remoteArena.id,
                remoteArena.idName,
                remoteArena.name,
                remoteArena.minTrophies)
    }
}