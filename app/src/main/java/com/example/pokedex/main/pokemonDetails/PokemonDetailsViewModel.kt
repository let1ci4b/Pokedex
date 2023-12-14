package com.example.pokedex.main.pokemonDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.database.PokemonEntity
import com.example.pokedex.main.model.DatabaseObject
import com.example.pokedex.main.model.Filter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailsViewModel : ViewModel() {
    private val db : AppDatabase? = DatabaseObject.database
    private val _pokemonCompleteList = MutableLiveData<List<PokemonEntity>>()
    val pokemonCompleteList: LiveData<List<PokemonEntity>> = _pokemonCompleteList
    var pokemonListFromDb : List<PokemonEntity>? = null

    fun getPokemonList() {
        GlobalScope.launch() {
            pokemonListFromDb =
                when(Filter.filter) {
                    Filter.FilterBy.NUMBER -> db?.pokemonDAO()?.getAll()?.sortedBy { pokemonEntity -> pokemonEntity.id }
                    Filter.FilterBy.NAME -> db?.pokemonDAO()?.getAll()?.sortedBy { pokemonEntity -> pokemonEntity.name }
                }
            withContext(Dispatchers.Main) {
                _pokemonCompleteList.value = pokemonListFromDb
            }
        }
    }
}