package com.example.pokedex.main.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.main.api.PokemonRepository
import com.example.pokedex.main.model.PokemonResponse
import com.example.pokedex.main.pokemonData.PokemonMutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MIN_POKEMON_ID = 1
private const val MAX_POKEMON_ID = 151

class MainViewModel : ViewModel() {
    private val repository = PokemonRepository()
    private val _pokemon = MutableLiveData<List<PokemonResponse>>()
    val pokemon: LiveData<List<PokemonResponse>>
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
}