package com.jux.ouiclashroyale.data.source.model

import com.jux.ouiclashroyale.data.Card

/**
 * Utility class that maps a RemoteCard to an Card
 */
class RemoteCardMapper {

    fun map(remoteCard: RemoteCard): Card {
        return Card(remoteCard.id,
                remoteCard.idName,
                remoteCard.rarity,
                remoteCard.type,
                remoteCard.name,
                remoteCard.description,
                remoteCard.arena,
                remoteCard.elixirCost)
    }
}