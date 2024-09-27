package com.example.pokedex.main.model

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.database.Converter

object DatabaseObject {

    var database : AppDatabase? = null

    fun setupDatabase(context: Context) {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Adicionando uma nova coluna `base_stats`
                database.execSQL("ALTER TABLE PokemonEntity ADD COLUMN base_stats TEXT")
            }
        }

        if (database == null) {
            database = Room
                .databaseBuilder(
                    context,
                    AppDatabase::class.java, "database-name"
                )
                .fallbackToDestructiveMigration()
                .addTypeConverter(Converter())
                .build()
        }
    }
}