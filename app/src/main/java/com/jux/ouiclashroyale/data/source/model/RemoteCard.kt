package com.jux.ouiclashroyale.data.source.model

import com.google.gson.annotations.SerializedName


data class RemoteCard(@SerializedName("_id") val id: String = "",
                      val idName: String = "",
                      val rarity: String = "",
                      val type: String = "",
                      val name: String = "",
                      val description: String = "",
                      val arena: Int = -1,
                      val elixirCost: Int = 0)