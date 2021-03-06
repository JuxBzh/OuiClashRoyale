package com.jux.ouiclashroyale.data.mapper

import com.jux.ouiclashroyale.data.source.mapper.RemoteArenaMapper
import com.jux.ouiclashroyale.data.source.model.RemoteArena
import org.junit.Assert
import org.junit.Test


class RemoteArenaMapperTest {
    private val mapper = RemoteArenaMapper()

    @Test
    fun mapArenas() {
        // GIVEN
        val remoteArenas = arrayOf(
                RemoteArena("id", "idName", "name", 10, 5,
                        arrayOf("1", "2")),
                RemoteArena("id2", "idName2", "name2", 20,
                        10, arrayOf("3"))
        )

        // WHEN
        val arenas = mapper.map(remoteArenas)

        // THEN
        Assert.assertEquals("Wrong number of arenas", 2, arenas.size)
        Assert.assertEquals("Index 0 - Wrong id", "id", arenas[0].id)
        Assert.assertEquals("Index 0 - Wrong idName", "idName", arenas[0].idName)
        Assert.assertEquals("Index 0 - Wrong name", "name", arenas[0].name)
        Assert.assertEquals("Index 0 - Wrong minTrophies", 10, arenas[0].minTrophies)
        Assert.assertEquals("Index 0 - Wrong goldPerVictory", 5, arenas[0].goldPerVictory)
        Assert.assertEquals("Index 0 - Wrong number of cards", 2, arenas[0].cards.size)
        Assert.assertEquals("Index 0 - Wrong card n°1", "1", arenas[0].cards[0])
        Assert.assertEquals("Index 0 - Wrong card n°2", "2", arenas[0].cards[1])
        Assert.assertEquals("Index 1 - Wrong id", "id2", arenas[1].id)
        Assert.assertEquals("Index 1 - Wrong idName", "idName2", arenas[1].idName)
        Assert.assertEquals("Index 1 - Wrong name", "name2", arenas[1].name)
        Assert.assertEquals("Index 1 - Wrong minTrophies", 20, arenas[1].minTrophies)
        Assert.assertEquals("Index 1 - Wrong goldPerVictory", 10, arenas[1].goldPerVictory)
        Assert.assertEquals("Index 1 - Wrong number of cards", 1, arenas[1].cards.size)
        Assert.assertEquals("Index 1 - Wrong card n°1", "3", arenas[1].cards[0])
    }

    @Test
    fun mapArena() {
        // GIVEN
        val remoteArena = RemoteArena("id", "idName", "name", 10,
                20, arrayOf("14"))

        // WHEN
        val arena = mapper.map(remoteArena)

        // THEN
        Assert.assertEquals("Wrong id", "id", arena.id)
        Assert.assertEquals("Wrong idName", "idName", arena.idName)
        Assert.assertEquals("Wrong name", "name", arena.name)
        Assert.assertEquals("Wrong minTrophies", 10, arena.minTrophies)
        Assert.assertEquals("Wrong goldPerVictory", 20, arena.goldPerVictory)
        Assert.assertEquals("Wrong number of cards", 1, arena.cards.size)
        Assert.assertEquals("Wrong card n°1", "14", arena.cards[0])
    }
}