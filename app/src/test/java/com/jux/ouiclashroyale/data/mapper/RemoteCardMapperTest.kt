package com.jux.ouiclashroyale.data.mapper

import com.jux.ouiclashroyale.data.source.mapper.RemoteCardMapper
import com.jux.ouiclashroyale.data.source.model.RemoteCard
import org.junit.Assert
import org.junit.Test


class RemoteCardMapperTest {
    private val mapper = RemoteCardMapper()

    @Test
    fun mapArena() {
        // GIVEN
        val remoteCard = RemoteCard("id", "idName", "common", "type",
                "name", "desc", 1, 4)

        // WHEN
        val card = mapper.map(remoteCard)

        // THEN
        Assert.assertEquals("Wrong id", "id", card.id)
        Assert.assertEquals("Wrong idName", "idName", card.idName)
        Assert.assertEquals("Wrong rarity", "common", card.rarity)
        Assert.assertEquals("Wrong type", "type", card.type)
        Assert.assertEquals("Wrong name", "name", card.name)
        Assert.assertEquals("Wrong description", "desc", card.description)
        Assert.assertEquals("Wrong arena", 1, card.arena)
        Assert.assertEquals("Wrong cost", 4, card.cost)
    }
}