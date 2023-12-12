package com.example.pokedex.main.model

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.database.Converter

object DatabaseObject {

    var database : AppDatabase? = null

    fun setupDatabase(context: Context) {
        if (database == null) {
            database = Room
                .databaseBuilder(
                    context,
                    AppDatabase::class.java, "database-name"
                )
                .addTypeConverter(Converter())
                .build()
        }
    }
}