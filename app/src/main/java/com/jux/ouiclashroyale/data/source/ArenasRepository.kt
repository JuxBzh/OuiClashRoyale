package com.jux.ouiclashroyale.data.source

/**
 * The entry point for fetching arenas either form the network or a local cache
 */
class ArenasRepository(private val remoteDataSource: ArenasDataSource) : ArenasDataSource {


    override fun getArena(id: String, callback: ArenasDataSource.ArenaCallback) {
    }

    override fun getArenas(callback: ArenasDataSource.ArenasCallback) {
    }
}