package com.example.pokedex.main.api

import com.example.pokedex.main.dto.PokemonDescriptionResponseDTO
import com.example.pokedex.main.dto.PokemonResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon/{id}/")
    suspend fun getSinglePokemon( @Path("id") id: Int? ) : PokemonResponseDTO

    @GET("pokemon-species/{id}/")
    suspend fun getPokemonDescription( @Path("id") id: Int? ) : PokemonDescriptionResponseDTO

}