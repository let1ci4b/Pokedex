package com.example.pokedex.main.dto

import com.google.gson.annotations.SerializedName // Retrofit recurse to indicate a JSON serialization

data class PokemonResponseDTO(
    @SerializedName("id")
    var id : Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("sprites")
    val sprites: SpritesDTO,
//    @SerializedName("types")
//    val types: List<SlotType>,
//    @SerializedName("moves")
//    val moves: List<Moves>,
    @SerializedName("flavor_text")
    val flavor_text: String
    )

