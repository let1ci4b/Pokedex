package com.example.pokedex.main.model

import android.content.Context
import androidx.room.Room
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.main.MainActivity

object Database {
    val database = Room.databaseBuilder(
        MainActivity(),
        AppDatabase::class.java, "database-name"
    ).build()

    /// TODO instance database once
//    private var database : AppDatabase? = null
//    fun getDatabase(context: Context) : AppDatabase {
//        if (database == null) {
//            database = Room.databaseBuilder(
//                MainActivity(),
//                AppDatabase::class.java, "database-name"
//            )  .build()
//        }
//        return database!!
//    }
}