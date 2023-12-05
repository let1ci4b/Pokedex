package com.example.pokedex.main.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.main.api.PokemonRepository
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.database.PokemonEntity
import com.example.pokedex.main.dto.PokemonResponseDTO
import com.example.pokedex.main.model.DatabaseObject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MIN_POKEMON_ID = 1
private const val MAX_POKEMON_ID = 151

class MainViewModel() : ViewModel() {
    private val repository = PokemonRepository()
    private val db : AppDatabase? = DatabaseObject.database
    private val _pokemon = MutableLiveData<PokemonEntity>()
    val pokemon: LiveData<PokemonEntity> = _pokemon
    private val _pokemonCompleteList = MutableLiveData<List<PokemonEntity>>()
    val pokemonCompleteList: LiveData<List<PokemonEntity>> = _pokemonCompleteList
    var pokemonListFromDb : List<PokemonEntity>? = null

@OptIn(DelicateCoroutinesApi::class)
fun getPokemon() {
        GlobalScope.launch() {
            pokemonListFromDb = db?.pokemonDAO()?.getAll()
            pokemonListFromDb?.let { pokemonList ->
                if (pokemonList.isEmpty()) {
                    downloadAllPokemons()
                } else if (pokemonList.size < MAX_POKEMON_ID) {
                    downloadMissingPokemons()
                } else {
                    /// TODO fix "java.lang.IllegalStateException: Cannot invoke setValue on a background thread"
                    _pokemonCompleteList.value = pokemonList
                }
            }
        }
    }

    /// TODO discover missing pokemons
    private fun downloadMissingPokemons() {
    }

    private suspend fun downloadAllPokemons() {
        try {
            for (i in MIN_POKEMON_ID..MAX_POKEMON_ID) {
                val pokemon = repository.getSinglePokemon(i)
                val entityPokemon = PokemonEntity(
                    pokemon.id,
                    pokemon.name,
                    pokemon.height,
                    pokemon.weight,
                    pokemon.sprites.other.officialArtwork.frontDefault,
                    listOf(pokemon.types[0].type.name), //, pokemon.types[1].toString()),
                    listOf(pokemon.abilities[0].ability.name.toString()), //pokemon.abilities[1].toString()),
                    pokemon.flavor_text)
                Log.e("VITINHO", "Pokemon recebido: " + pokemon.name)
                db?.pokemonDAO()?.insertPokemons(entityPokemon)

                withContext(Dispatchers.Main) {
                    _pokemon.value = entityPokemon
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    fun filterPokemonList(query: String?, recyclerViewAdapter: RecyclerViewAdapter) : Boolean {
//        val filteredlist: ArrayList<PokemonResponseDTO> = ArrayList()
//
//        try {
//            pokemonData?.forEach { item ->
//                if (item.name.contains(query!!, ignoreCase = true) || item.id.toString().contains(query)) {
//                    filteredlist.add(item)
//                }
//            }
//        } catch (Exception: Exception) {
//            return true
//        }
//
//        return if(filteredlist.isEmpty() && !query?.isEmpty()!!) false
//        else {
//            recyclerViewAdapter.filterList(filteredlist)
//            true
//        }
//    }
}