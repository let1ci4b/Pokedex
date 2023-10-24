package com.example.pokedex.main.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("id")
    var id : Int? = null,
    @SerializedName("name")
    val name: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("sprites")
    val sprites: Sprites,
    @SerializedName("types")
    val types: List<SlotType>
    )

