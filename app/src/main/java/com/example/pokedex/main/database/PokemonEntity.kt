package com.example.pokedex.main.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey var id : Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo ("height") val height: Int?,
    @ColumnInfo ("weight") val weight: Int?,
    @ColumnInfo ("sprites") val sprite: String,
    @ColumnInfo ("types") val types: List<String>,
    @ColumnInfo ("abilities") val abilities: List<String>,
    @ColumnInfo("base_stats") val base_stats: List<String>,
    @ColumnInfo("flavor_text") val flavor_text: String?,
)
