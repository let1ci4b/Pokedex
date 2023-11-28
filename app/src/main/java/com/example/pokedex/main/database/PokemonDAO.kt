package com.example.pokedex.main.database

import androidx.room.Query

interface PokemonDAO {
    @Query("SELECT * FROM pokemonentity")
    fun getAll(): List<PokemonEntity>
}