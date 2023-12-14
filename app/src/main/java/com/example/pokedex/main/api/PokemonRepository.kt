package com.example.pokedex.main.api

import com.example.pokedex.main.dto.PokemonDescriptionResponseDTO
import com.example.pokedex.main.dto.PokemonResponseDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonRepository {
    private val service: PokemonService = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokemonService::class.java)

    suspend fun getSinglePokemon(id: Int) : PokemonResponseDTO? {
        return try {
            service.getSinglePokemon(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getPokemonDescription(id: Int) : PokemonDescriptionResponseDTO? {
        return try {
            service.getPokemonDescription(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}