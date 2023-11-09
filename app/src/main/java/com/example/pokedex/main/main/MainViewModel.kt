package com.example.pokedex.main.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.main.api.PokemonRepository
import com.example.pokedex.main.dto.PokemonResponseDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MIN_POKEMON_ID = 1
private const val MAX_POKEMON_ID = 151

class MainViewModel : ViewModel() {
    private val repository = PokemonRepository()
    private val _pokemon = MutableLiveData<List<PokemonResponseDTO>>()
    val pokemon: LiveData<List<PokemonResponseDTO>> = _pokemon

    var pokemonData : MutableList<PokemonResponseDTO> = mutableListOf()
        private set


    fun getPokemon() {
        viewModelScope.launch(Dispatchers.IO) {

            for (i in MIN_POKEMON_ID..MAX_POKEMON_ID) {
                pokemonData.add(repository.getSinglePokemon(i))
                withContext(Dispatchers.Main) {
                    _pokemon.value = _pokemon.value.orEmpty() + repository.getSinglePokemon(i)
                }
            }
        }
    }

    fun filterPokemonList(query: String?, recyclerViewAdapter: RecyclerViewAdapter) : Boolean {
        val filteredlist: ArrayList<PokemonResponseDTO> = ArrayList()

        try {
            pokemonData.forEach { item ->
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