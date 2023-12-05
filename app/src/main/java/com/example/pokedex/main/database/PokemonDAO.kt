package com.example.pokedex.main.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDAO {
    @Query("SELECT * FROM pokemonentity")
    fun getAll(): List<PokemonEntity>

    @Query("SELECT * FROM pokemonentity WHERE id LIKE :pokemonID")
    fun getPokemonById(pokemonID: Int): PokemonEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemons(vararg pokemons: PokemonEntity)
}