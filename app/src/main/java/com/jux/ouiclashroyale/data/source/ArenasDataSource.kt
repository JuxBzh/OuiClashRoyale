package com.jux.ouiclashroyale.data.source

import com.jux.ouiclashroyale.data.Arena

/**
 * An interface defining how arenas can be accessed
 */
interface ArenasDataSource {

    /**
     * A callback interface to get notified when an arena is loaded
     */
    interface ArenaCallback {
        fun onArenaLoaded(arena: Arena)
        fun onError()
    }

    /**
     * A callback interface to get notified when a list of arenas is loaded
     */
    interface ArenasCallback {
        fun onArenasLoaded(arenas: Array<Arena>)
        fun onError()
    }

    /**
     * Fetch the arena with the given ID
     */
    fun getArena(id: String, callback: ArenaCallback)

    /**
     * Fetch all arenas
     */
    fun getArenas(callback: ArenasCallback)
}