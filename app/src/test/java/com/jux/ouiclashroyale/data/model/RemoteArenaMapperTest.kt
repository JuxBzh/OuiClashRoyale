package com.jux.ouiclashroyale.data.model

import com.jux.ouiclashroyale.data.source.model.RemoteArena
import com.jux.ouiclashroyale.data.source.model.RemoteArenaMapper
import org.junit.Assert
import org.junit.Test


class RemoteArenaMapperTest {
    private val mapper = RemoteArenaMapper()

    @Test
    fun mapTest() {
        // GIVEN
        val remoteArenas = arrayOf(
                RemoteArena("id", "idName", "name", 10),
                RemoteArena("id2", "idName2", "name2", 20)
        )

        // WHEN
        val arenas = mapper.map(remoteArenas)

        // THEN
        Assert.assertEquals("Wrong number of arenas", 2, arenas.size)
        Assert.assertEquals("Index 0 - Wrong id", "id", arenas[0].id)
        Assert.assertEquals("Index 0 - Wrong idName", "idName", arenas[0].idName)
        Assert.assertEquals("Index 0 - Wrong name", "name", arenas[0].name)
        Assert.assertEquals("Index 0 - Wrong minTrophies", 10, arenas[0].minTrophies)
        Assert.assertEquals("Index 1 - Wrong id", "id2", arenas[1].id)
        Assert.assertEquals("Index 1 - Wrong idName", "idName2", arenas[1].idName)
        Assert.assertEquals("Index 1 - Wrong name", "name2", arenas[1].name)
        Assert.assertEquals("Index 1 - Wrong minTrophies", 20, arenas[1].minTrophies)
    }
}