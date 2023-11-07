package com.example.pokedex.main.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.main.api.PokemonRepository
import com.example.pokedex.main.dto.PokemonResponseDTO
import com.example.pokedex.main.pokemonData.PokemonMutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MIN_POKEMON_ID = 1
private const val MAX_POKEMON_ID = 151

class MainViewModel : ViewModel() {
    private val repository = PokemonRepository()
    private val _pokemon = MutableLiveData<List<PokemonResponseDTO>>()
    val pokemon: LiveData<List<PokemonResponseDTO>>
        get() = _pokemon

    fun getPokemon() {
        viewModelScope.launch(Dispatchers.IO) {

            for (i in MIN_POKEMON_ID..MAX_POKEMON_ID) {
                PokemonMutableList.pokemonData.add(repository.getSinglePokemon(i))
            }

            withContext(Dispatchers.Main) {
                _pokemon.postValue(PokemonMutableList.pokemonData.toList())
            }
        }
    }

    fun filterPokemonList(query: String?, recyclerViewAdapter: RecyclerViewAdapter) : Boolean {
        val filteredlist: ArrayList<PokemonResponseDTO> = ArrayList()

        try {
            PokemonMutableList.pokemonData.forEach { item ->
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