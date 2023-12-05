package com.example.pokedex.main.model

import com.example.pokedex.main.database.AppDatabase

object DatabaseObject {
    var database: AppDatabase? = null

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