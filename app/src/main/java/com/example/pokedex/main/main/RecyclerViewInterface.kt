package com.example.pokedex.main.main

import com.example.pokedex.main.dto.PokemonResponseDTO

interface RecyclerViewInterface {

    fun onPokemonClicked(pokemon: PokemonResponseDTO)

}