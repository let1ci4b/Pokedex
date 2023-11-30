package com.example.pokedex.main.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.main.dto.PokemonResponseDTO

interface PokemonDAO {
    @Query("SELECT * FROM pokemonresponsedto")
    fun getAll(): List<PokemonResponseDTO>

    @Query("SELECT * FROM pokemonresponsedto WHERE id LIKE :pokemonID")
    fun getPokemonById(pokemonID: Int): PokemonResponseDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemons(vararg pokemons: PokemonResponseDTO)
}