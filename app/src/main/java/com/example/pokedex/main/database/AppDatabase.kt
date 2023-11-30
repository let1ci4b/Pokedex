package com.example.pokedex.main.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex.main.dto.PokemonResponseDTO

@Database(entities = [PokemonResponseDTO::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

        abstract fun pokemonDAO(): PokemonDAO
}