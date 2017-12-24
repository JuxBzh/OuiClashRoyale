package com.jux.ouiclashroyale.data


data class Card(val id: String = "",
                val idName: String = "",
                val rarity: String = "",
                val type: String = "",
                val name: String = "",
                val description: String = "",
                val arena: Int = -1,
                val cost: Int = 0)