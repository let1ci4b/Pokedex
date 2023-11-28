package com.example.pokedex.main.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedex.main.dto.PokemonResponseDTO

@Entity
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val pokemon: PokemonResponseDTO
)
