package com.example.pokedex.main.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.main.api.PokemonRepository
import com.example.pokedex.main.database.AppDatabase
import com.example.pokedex.main.database.PokemonEntity
import com.example.pokedex.main.dto.PokemonDescriptionResponseDTO
import com.example.pokedex.main.dto.PokemonResponseDTO
import com.example.pokedex.main.model.Constants
import com.example.pokedex.main.model.DatabaseObject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

class MainViewModel() : ViewModel() {
    private val repository = PokemonRepository()
    private val db : AppDatabase? = DatabaseObject.database
    var jobCoroutine: Job? = null

    private val _pokemonLiveData = MutableLiveData<PokemonEntity>()
    val pokemonLiveData: LiveData<PokemonEntity> = _pokemonLiveData
    private val _pokemonCompleteListLiveData = MutableLiveData<List<PokemonEntity>>()
    val pokemonCompleteListLiveData: LiveData<List<PokemonEntity>> = _pokemonCompleteListLiveData

    private var pokemonList : MutableList<PokemonEntity> = mutableListOf()

    @OptIn(DelicateCoroutinesApi::class)
    fun getPokemon() {
        jobCoroutine = GlobalScope.launch {
            pokemonList.clear()
            db?.pokemonDAO()?.getAll()?.sortedBy { pokemonEntity -> pokemonEntity.id }
                ?.let { pokemonList.addAll(it) }

            if (pokemonList.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    _pokemonCompleteListLiveData.value = pokemonList
                }
            }

            if (pokemonList.isEmpty()) downloadAllPokemons()
            else if (pokemonList.size < Constants.MAX_POKEMON_ID) downloadMissingPokemons()
        }
    }

    private suspend fun downloadMissingPokemons() {
        for (i in Constants.MIN_POKEMON_ID..Constants.MAX_POKEMON_ID) {
            yield()
            if (pokemonList.find { pokemonEntity -> pokemonEntity.id == i } != null) continue
            else {
                val pokemon = repository.getSinglePokemon(i)
                val pokemonDescription = repository.getPokemonDescription(i)
                addPokemonInDb(pokemon, pokemonDescription)
            }
        }
    }

    private suspend fun downloadAllPokemons() {
        for (i in Constants.MIN_POKEMON_ID..Constants.MAX_POKEMON_ID) {
            yield()
            val pokemon = repository.getSinglePokemon(i)
            val pokemonDescription = repository.getPokemonDescription(i)
            addPokemonInDb(pokemon, pokemonDescription)
        }
    }

    private suspend fun addPokemonInDb(pokemon: PokemonResponseDTO, pokemonDescription: PokemonDescriptionResponseDTO) {
        try {
            val entityPokemon = PokemonEntity(
                pokemon.id,
                pokemon.name,
                pokemon.height,
                pokemon.weight,
                pokemon.sprites.other.officialArtwork.frontDefault,
                pokemon.types.map { it.type.name },
                pokemon.abilities.map { it.ability.name.toString() },
                pokemon.stats.map { it.base_stat.toString() },
                getPokemonDescription(pokemonDescription))
            Log.e("POKEMON", "Pokemon recebido: " + pokemon.name)
            db?.pokemonDAO()?.insertPokemons(entityPokemon)
            withContext(Dispatchers.Main) {
                _pokemonLiveData.value = entityPokemon
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getPokemonDescription(pokemonDescription: PokemonDescriptionResponseDTO) : String? {
        var desc: String? = null
        for (i in 0..pokemonDescription.text_entries.size+1) {
            if(pokemonDescription.text_entries[i].language.name == "en") {
                desc = pokemonDescription.text_entries[i].flavor_text
                    .replace("\n", " ")
                    .replace("POKéMON", "pokemon")
                break
            } else continue
        }
        return desc
    }

    /// TODO setup filter validation
    fun filterPokemonList(query: String?, recyclerViewAdapter: RecyclerViewAdapter) : Boolean {
        val filteredlist: ArrayList<PokemonEntity> = ArrayList()

        try {
            pokemonList.forEach { item ->
                if (item.name.contains(query!!, ignoreCase = true) || item.id.toString().contains(query)) {
                    filteredlist.add(item)
                }
            }
        } catch (Exception: Exception) {
            return true
        }

        return if(filteredlist.isEmpty() && !query?.isEmpty()!!) false
        else {
            recyclerViewAdapter.filterList(filteredlist)
            true
        }
    }
}