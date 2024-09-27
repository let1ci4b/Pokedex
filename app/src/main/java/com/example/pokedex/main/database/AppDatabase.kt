package com.example.pokedex.main.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex.main.dto.PokemonResponseDTO

@Database(entities = [PokemonEntity::class], version = 2)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

        abstract fun pokemonDAO(): PokemonDAO
}